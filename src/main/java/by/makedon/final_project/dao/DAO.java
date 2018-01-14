package by.makedon.final_project.dao;

import by.makedon.final_project.connectionpool.ProxyConnection;
import by.makedon.final_project.entity.User;
import by.makedon.final_project.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public interface DAO {
    Logger LOGGER = LogManager.getLogger(DAO.class);

    void addUser(User user) throws DAOException;
    boolean match(String param1, String param2) throws DAOException;
    String takeUserType(String usernameValue) throws DAOException;
    String takeUsername(String emailValue) throws DAOException;
    void changePassword(User user) throws DAOException;

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