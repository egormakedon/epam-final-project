package by.makedon.final_project.dao;

import by.makedon.final_project.connectionpool.ConnectionPool;
import by.makedon.final_project.connectionpool.ProxyConnection;
import by.makedon.final_project.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO implements DAO {
    private static final DAO INSTANCE = new RegistrationDAO();
    private static final String SQL_SELECT_USER_ID_BY_EMAIL_USERNAME = "SELECT user_id FROM user WHERE email=? OR username=?";
    private static final String SQL_INSERT_USER = "INSERT INTO user(email,username,password) VALUES(?,?,SHA1(?))";

    private RegistrationDAO(){}

    public static DAO getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean match(String emailValue, String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_ID_BY_EMAIL_USERNAME);
            statement.setString(1, emailValue);
            statement.setString(2, usernameValue);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void addUser(String emailValue, String usernameValue, String passwordValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (isUserExist(connection, emailValue, usernameValue)) {
                return false;
            }
            statement = connection.prepareStatement(SQL_INSERT_USER);
            statement.setString(1, emailValue);
            statement.setString(2, usernameValue);
            statement.setString(3, passwordValue);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public String takeUserType(String usernameValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
    @Override
    public String takeUsername(String emailValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
    @Override
    public void changePassword(String usernameValue, String passwordValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
}
