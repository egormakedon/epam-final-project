package by.makedon.finalproject.dao.basedao;

import by.makedon.finalproject.connectionpool.ConnectionPool;
import by.makedon.finalproject.connectionpool.ProxyConnection;
import by.makedon.finalproject.entity.User;
import by.makedon.finalproject.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordDAO implements BaseDAO {
    private static final BaseDAO INSTANCE = new ChangePasswordDAO();
    private static final String USERNAME = "username";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE user SET password=SHA1(?) WHERE username=?";
    private static final String SQL_SELECT_USER_ID_BY_USERNAME = "SELECT user_id FROM user WHERE username=?";
    private static final String SQL_SELECT_USERNAME_BY_EMAIL = "SELECT username FROM user WHERE email=?";

    private ChangePasswordDAO() {}

    public static BaseDAO getInstance() {
        return INSTANCE;
    }

    @Override
    public void changePassword(User user) throws DAOException {
        String usernameValue = user.getUsernameValue();
        String passwordValue = user.getPasswordValue();

        ProxyConnection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_ID_BY_USERNAME);
            statement.setString(1, usernameValue);
            System.out.println(usernameValue);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException(usernameValue + " this user doesn't exist");
            }
            close(statement);
            statement = connection.prepareStatement(SQL_UPDATE_PASSWORD);
            statement.setString(1, passwordValue);
            statement.setString(2, usernameValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean isUsernameExist(String usernameValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
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
    public void addUser(User user) throws DAOException {
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
