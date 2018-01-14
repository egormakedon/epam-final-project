package by.makedon.final_project.dao;

import by.makedon.final_project.connectionpool.ConnectionPool;
import by.makedon.final_project.connectionpool.ProxyConnection;
import by.makedon.final_project.entity.User;
import by.makedon.final_project.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO implements DAO {
    private static final DAO INSTANCE = new LoginDAO();
    private static final String SQL_SELECT_USER_ID_BY_USERNAME_PASSWORD = "SELECT user_id FROM user WHERE username=? AND password=SHA1(?)";
    private static final String SQL_SELECT_TYPE_BY_USERNAME = "SELECT type FROM user WHERE username=?";

    private LoginDAO() {}

    public static DAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void addUser(User user) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }

    @Override
    public boolean match(String usernameValue, String passwordValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_ID_BY_USERNAME_PASSWORD);
            statement.setString(1, usernameValue);
            statement.setString(2, passwordValue);
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
    public String takeUserType(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_TYPE_BY_USERNAME);
            statement.setString(1, usernameValue);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("type");
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public String takeUsername(String emailValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }

    @Override
    public void changePassword(User user) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
}



