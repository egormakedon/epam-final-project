package by.makedon.final_project.connectionpool;

import by.makedon.final_project.exception.CloneException;
import by.makedon.final_project.exception.FatalException;
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
            LOGGER.log(Level.FATAL, "Tried to clone singleton object with reflection api");
            throw new FatalException("Tried to clone singleton object with reflection api");
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
            try {
                ProxyConnection proxyConnection = connectionQueue.take();
                proxyConnection.closeConnection();
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARN, e);
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
            LOGGER.log(Level.ERROR, "Tried to clone singleton object");
            throw new CloneException("Tried to clone singleton object");
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
            LOGGER.log(Level.FATAL, "Mysql jdbc driver hasn't loaded");
            throw new FatalException("Mysql jdbc driver hasn't loaded");
        }
    }
    private static void initPool() {
        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle("db");
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "Hasn't found db.properties");
            throw new FatalException("Hasn't found db.properties");
        }

        final String URL;
        final String USER;
        final String PASSWORD;
        final String CHARACTER_ENCODING;
        final String USE_UNICODE;
        final String POOL_SIZE;

        try {
            URL = resourceBundle.getString("db.url");
            USER = resourceBundle.getString("db.user");
            PASSWORD = resourceBundle.getString("db.password");
            CHARACTER_ENCODING = resourceBundle.getString("db.characterEncoding");
            USE_UNICODE = resourceBundle.getString("db.useUnicode");
            POOL_SIZE = resourceBundle.getString("db.poolSize");
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "Wrong case in db.properties");
            throw new FatalException("Wrong case in db.properties");
        }

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
                LOGGER.log(Level.FATAL, "Hasn't found connection with DB");
                throw new FatalException("Hasn't found connection with DB");
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