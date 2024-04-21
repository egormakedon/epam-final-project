package by.makedon.selectioncommittee.app.dao.base;

import by.makedon.selectioncommittee.connectionpool.ConnectionPool;
import by.makedon.selectioncommittee.connectionpool.ProxyConnection;
import by.makedon.selectioncommittee.app.entity.user.User;
import by.makedon.selectioncommittee.app.dao.DAOException;
import org.jetbrains.annotations.NotNull;
import org.apache.logging.log4j.Level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class BaseDAOImpl implements BaseDAO {
    private static final BaseDAO INSTANCE;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    static {
        INSTANCE = new BaseDAOImpl();
        instanceCreated.set(true);
    }
    private BaseDAOImpl(){
        if (instanceCreated.get()) {
            LOGGER.log(Level.FATAL, "Tried to create singleton object with reflection api");
            throw new RuntimeException("Tried to create singleton object with reflection api");
        }
    }
    public static BaseDAO getInstance() {
        return INSTANCE;
    }

    private static final String USERNAME = "username";
    private static final String TYPE = "type";

    private static final String SQL_UPDATE_PASSWORD = "UPDATE user SET password=SHA1(?) WHERE username=?";
    private static final String SQL_SELECT_USERNAME_BY_EMAIL = "SELECT username FROM user WHERE email=?";
    private static final String SQL_SELECT_USER_ID_BY_USERNAME_PASSWORD = "SELECT user_id FROM user WHERE username=? AND password=SHA1(?)";
    private static final String SQL_SELECT_USER_ID_BY_USERNAME = "SELECT user_id FROM user WHERE username=?";
    private static final String SQL_SELECT_TYPE_BY_USERNAME = "SELECT type FROM user WHERE username=?";
    private static final String SQL_SELECT_USER_ID_BY_EMAIL_USERNAME = "SELECT user_id FROM user WHERE email=? OR username=?";
    private static final String SQL_INSERT_USER = "INSERT INTO user(email,username,password) VALUES(?,?,SHA1(?))";

    @Override
    public void addUser(@NotNull User user) throws DAOException {
        String emailValue = user.getEmailValue();
        String usernameValue = user.getUsernameValue();
        String passwordValue = user.getPasswordValue();

        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            if (matchEmailUsername(emailValue, usernameValue)) {
                throw new DAOException("this user already exist");
            }
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_USER);
            statement.setString(1, emailValue);
            statement.setString(2, usernameValue);
            statement.setString(3, passwordValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean matchUsernamePassword(String usernameValue, String passwordValue) throws DAOException {
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
    public boolean matchEmailUsername(String emailValue, String usernameValue) throws DAOException {
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
    public String takeTypeByUsername(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_TYPE_BY_USERNAME);
            statement.setString(1, usernameValue);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(TYPE);
            } else {
                throw new DAOException("user " + usernameValue + " doesn't exist");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public String takeUsernameByEmail(String emailValue) throws DAOException {
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
    public void changePasswordByUsername(String usernameValue, String passwordValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_PASSWORD);
            statement.setString(1, passwordValue);
            statement.setString(2, usernameValue);
            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new DAOException("user " + usernameValue + " doesn't exist");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean matchUsername(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_ID_BY_USERNAME);
            statement.setString(1, usernameValue);
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
    public Object clone() throws CloneNotSupportedException {
        LOGGER.log(Level.ERROR, "Tried to clone singleton object");
        throw new CloneNotSupportedException("Tried to clone singleton object");
    }
}
