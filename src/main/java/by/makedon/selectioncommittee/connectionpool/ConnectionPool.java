package by.makedon.selectioncommittee.connectionpool;

import by.makedon.selectioncommittee.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static BlockingQueue<ProxyConnection> connectionQueue;
    private static int poolSize;

    private ConnectionPool() {
        if (instanceCreated.get()) {
            LOGGER.log(Level.FATAL, "Tried to clone connection pool with reflection api");
            throw new RuntimeException("Tried to clone connection pool with reflection api");
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ConnectionPool();
                    registerJDBCDriver();
                    initPool();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, e);
        }
        return proxyConnection;
    }

    public void destroyConnections() {
        for (int index = 0; index < poolSize; index++) {
            ProxyConnection proxyConnection = null;
            try {
                proxyConnection = connectionQueue.take();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARN, e);
            } finally {
                if (proxyConnection != null) {
                    try {
                        proxyConnection.closeConnection();
                    } catch (DAOException e) {
                        LOGGER.log(Level.WARN, e);
                    }
                }
            }
        }

        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (instanceCreated.get()) {
            LOGGER.log(Level.ERROR, "Tried to clone clone connection pool");
            throw new CloneNotSupportedException("Tried to clone clone connection pool");
        }
        return super.clone();
    }

    void releaseConnection(ProxyConnection proxyConnection) {
        try {
            connectionQueue.put(proxyConnection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, e);
        }
    }

    private static void registerJDBCDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "Mysql jdbc driver hasn't loaded", e);
            throw new RuntimeException("Mysql jdbc driver hasn't loaded", e);
        }
    }

    private static void initPool() {
        final String DATABASE_PROPERTY = "property.database";
        final String DATABASE_URL = "db.url";
        final String DATABASE_USER = "db.user";
        final String DATABASE_PASSWORD = "db.password";
        final String DATABASE_CHARACTER_ENCODING = "db.characterEncoding";
        final String DATABASE_USE_UNICODE = "db.useUnicode";
        final String DATABASE_POOL_SIZE = "db.poolSize";

        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY);
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "Hasn't found bundle for database");
            throw new RuntimeException("Hasn't found bundle for database");
        }

        final String URL = resourceBundle.getString(DATABASE_URL);
        final String USER = resourceBundle.getString(DATABASE_USER);
        final String PASSWORD = resourceBundle.getString(DATABASE_PASSWORD);
        final String CHARACTER_ENCODING = resourceBundle.getString(DATABASE_CHARACTER_ENCODING);
        final String USE_UNICODE = resourceBundle.getString(DATABASE_USE_UNICODE);
        final String POOL_SIZE = resourceBundle.getString(DATABASE_POOL_SIZE);

        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);
        properties.put("characterEncoding", CHARACTER_ENCODING);
        properties.put("useUnicode", USE_UNICODE);

        poolSize = Integer.parseInt(POOL_SIZE);
        connectionQueue = new ArrayBlockingQueue<ProxyConnection>(poolSize);

        for (int index = 0; index < poolSize; index++) {
            Connection connection;
            try {
                connection = DriverManager.getConnection(URL, properties);
            } catch (SQLException e) {
                LOGGER.log(Level.FATAL, "Hasn't found connection with database");
                throw new RuntimeException("Hasn't found connection with database");
            }

            ProxyConnection proxyConnection = new ProxyConnection(connection);
            try {
                connectionQueue.put(proxyConnection);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARN, e);
            }
        }
    }
}