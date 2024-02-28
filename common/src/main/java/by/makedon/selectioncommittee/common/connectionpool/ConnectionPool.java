package by.makedon.selectioncommittee.common.connectionpool;

import by.makedon.selectioncommittee.common.resource.PropertyHolder;
import by.makedon.selectioncommittee.common.resource.ResourceUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import static by.makedon.selectioncommittee.common.connectionpool.DatabasePropertyConstants.*;

@Slf4j
public final class ConnectionPool {
    public static final int DEFAULT_POOL_SIZE = 5;
    public static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";
    public static final String DEFAULT_USE_UNICODE = Boolean.TRUE.toString();
    public static final int DEFAULT_LOGIN_TIMEOUT_SECONDS = 10;

    private static final AtomicBoolean isInstanceCreated = new AtomicBoolean(false);
    private static final ReentrantLock instanceCreationLock = new ReentrantLock();

    private static ConnectionPool instance;
    private BlockingQueue<Connection> connectionQueue;
    private int poolSize = DEFAULT_POOL_SIZE;

    private ConnectionPool() {
        if (isInstanceCreated.get() || instance != null) {
            final String message = "ConnectionPool instance has been already created " +
                "or it is attempt to clone ConnectionPool using Reflection API";
            log.error(message);
            throw new ConnectionPoolException(message);
        }
        registerJdbcDriver();
        initPool();
        log.debug("ConnectionPool instance is created successfully");
    }

    public static ConnectionPool getInstance() {
        if (!isInstanceCreated.get()) {
            instanceCreationLock.lock();
            log.debug("ConnectionPool: creation lock!");
            try {
                if (!isInstanceCreated.get()) {
                    instance = new ConnectionPool();
                    isInstanceCreated.set(true);
                }
            } finally {
                instanceCreationLock.unlock();
                log.debug("ConnectionPool: creation unlock!");
            }
        }
        return instance;
    }
    private void registerJdbcDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            log.debug("MySQL JDBC driver is registered successfully");
        } catch (SQLException e) {
            final String message = "MySQL JDBC driver has not been loaded";
            throw new ConnectionPoolException(message, e);
        }
    }
    private void initPool() {
        PropertyHolder databaseResource = ResourceUtil.resource(DATABASE_RESOURCE_NAME);

        final String message = String.format("Required property `%s` is missed", URL_PROPERTY_KEY);
        String urlPropertyValue = databaseResource
            .property(URL_PROPERTY_KEY)
            .orElseThrow(() -> new ConnectionPoolInitializationException(message));
        Properties databaseProperties = createDatabaseProperties(databaseResource);

        poolSize = databaseResource
            .property(POOL_SIZE_PROPERTY_KEY)
            .map(Integer::parseInt)
            .orElse(DEFAULT_POOL_SIZE);
        connectionQueue = new ArrayBlockingQueue<>(poolSize);

        int loginTimeout = databaseResource
            .property(LOGIN_TIMEOUT)
            .map(Integer::parseInt)
            .orElse(DEFAULT_LOGIN_TIMEOUT_SECONDS);
        DriverManager.setLoginTimeout(loginTimeout);

        acquireDatabaseConnections(urlPropertyValue, databaseProperties);
        log.info("ConnectionPool has been initialised successfully");
    }
    @NotNull
    private Properties createDatabaseProperties(@NotNull PropertyHolder databaseResource) {
        final String messageTemplate = "Required property `%s` is missed";

        String userPropertyValue = databaseResource
            .property(USER_PROPERTY_KEY)
            .orElseThrow(() -> new ConnectionPoolInitializationException(String.format(messageTemplate, USER_PROPERTY_KEY)));
        String passwordPropertyValue = databaseResource
            .property(PASSWORD_PROPERTY_KEY)
            .orElseThrow(() -> new ConnectionPoolInitializationException(String.format(messageTemplate, PASSWORD_PROPERTY_KEY)));
        String characterEncodingPropertyValue = databaseResource
            .property(CHARACTER_ENCODING_PROPERTY_KEY)
            .orElse(DEFAULT_CHARACTER_ENCODING);
        String useUnicodePropertyValue = databaseResource
            .property(USE_UNICODE_PROPERTY_KEY)
            .orElse(DEFAULT_USE_UNICODE);

        Properties properties = new Properties();
        properties.put("user", userPropertyValue);
        properties.put("password", passwordPropertyValue);
        properties.put("characterEncoding", characterEncodingPropertyValue);
        properties.put("useUnicode", useUnicodePropertyValue);
        return properties;
    }
    @SneakyThrows
    private void acquireDatabaseConnections(@NotNull String urlPropertyValue, @NotNull Properties databaseProperties) {
        for (int index = 0; index < poolSize; index++) {
            Connection connection;
            try {
                log.debug("Trying to create new database connection [{}]...", index);
                connection = DriverManager.getConnection(urlPropertyValue, databaseProperties);
                log.debug("New database connection [{}] has been created successfully", index);
            } catch (SQLException e) {
                throw new ConnectionPoolInitializationException(e.getMessage(), e);
            }
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            connectionQueue.put(proxyConnection);
        }
    }

    @SneakyThrows
    public Connection getConnection() {
        log.debug("Trying to take connection from pool...");
        Connection connection = connectionQueue.take();
        log.debug("Connection has been taken from pool");
        return connection;
    }

    public void destroy() {
        closeConnections();
        deregisterDrivers();
        log.info("ConnectionPool has been destroyed successfully");
    }
    @SneakyThrows
    private void closeConnections() {
        for (int index = 0; index < poolSize; index++) {
            ProxyConnection proxyConnection = null;
            try {
                proxyConnection = (ProxyConnection) getConnection();
            } finally {
                if (Objects.nonNull(proxyConnection)) {
                    log.debug("Trying to close database connection [{}]", index);
                    proxyConnection.closeConnection();
                    log.debug("Database connection [{}] has been closed successfully", index);
                }
            }
        }
        log.info("All connections have been closed successfully");
    }
    @SneakyThrows
    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            DriverManager.deregisterDriver(driver);
        }
        log.info("All drivers have been deregistered successfully");
    }

    @SneakyThrows
    void releaseConnection(Connection connection) {
        connectionQueue.put(connection);
        log.debug("Release connection and back it to the pool");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (isInstanceCreated.get()) {
            final String message = "Trying to clone ConnectionPool";
            log.error(message);
            throw new CloneNotSupportedException(message);
        }
        return super.clone();
    }
}
