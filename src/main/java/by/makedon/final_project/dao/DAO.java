package by.makedon.final_project.dao;

import by.makedon.final_project.connectionpool.ProxyConnection;
import by.makedon.final_project.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public interface DAO {
    Logger LOGGER = LogManager.getLogger(DAO.class);

    boolean addUser(String emailValue, String usernameValue, String passwordValue) throws DAOException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
        }
    }

    default void close(ProxyConnection proxyConnection) {
        try {
            if (proxyConnection != null) {
                proxyConnection.close();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
        }
    }
}