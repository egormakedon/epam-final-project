package by.makedon.final_project.dao;

import by.makedon.final_project.connectionpool.ConnectionPool;
import by.makedon.final_project.connectionpool.ProxyConnection;
import by.makedon.final_project.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MailDAO implements DAO {
    private static final DAO INSTANCE = new MailDAO();
    private static final String USERNAME = "username";
    private static final String SQL_SELECT_USERNAME_BY_EMAIL = "SELECT username FROM user WHERE email=?";

    private MailDAO() {}

    public static DAO getInstance() {
        return INSTANCE;
    }

    @Override
    public String takeUsername(String emailValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USERNAME_BY_EMAIL);
            statement.setString(1, emailValue);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(USERNAME);
            } else {
                throw new DAOException("user doesn't exist");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void changePassword(String usernameValue, String passwordValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }

    @Override
    public void addUser(String emailValue, String usernameValue, String passwordValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
    @Override
    public boolean match(String usernameValue, String passwordValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
    @Override
    public String takeUserType(String usernameValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
}
