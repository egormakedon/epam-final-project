package by.makedon.final_project.connectionpool;

import by.makedon.final_project.exception.CloneException;
import by.makedon.final_project.exception.FatalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();

    private static Deque<ProxyConnection> connectionDeque = new ArrayDeque<ProxyConnection>();

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

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (instanceCreated.get()) {
            LOGGER.log(Level.ERROR, "Tried to clone singleton object");
            throw new CloneException("Tried to clone singleton object");
        }
        return super.clone();
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
        ResourceBundle resourceBundle = null;
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
            LOGGER.log(Level.FATAL, e);
            throw new FatalException(e);
        }

        Properties properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);
        properties.put("characterEncoding", CHARACTER_ENCODING);
        properties.put("useUnicode", USE_UNICODE);

        for (int index = 0; index < Integer.parseInt(POOL_SIZE); index++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(URL, properties);
            } catch (SQLException e) {
                LOGGER.log(Level.FATAL, e);
                throw new FatalException(e);
            }
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            connectionDeque.push(proxyConnection);
        }
    }
}
