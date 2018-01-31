package by.makedon.selectioncommittee.dao.basedao;

import by.makedon.selectioncommittee.connectionpool.ConnectionPool;
import by.makedon.selectioncommittee.connectionpool.ProxyConnection;
import by.makedon.selectioncommittee.entity.User;
import by.makedon.selectioncommittee.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO implements BaseDAO {
    private static final BaseDAO INSTANCE = new RegistrationDAO();
    private static final String SQL_SELECT_USER_ID_BY_EMAIL_USERNAME = "SELECT user_id FROM user WHERE email=? OR username=?";
    private static final String SQL_INSERT_USER = "INSERT INTO user(email,username,password) VALUES(?,?,SHA1(?))";

    private RegistrationDAO(){}

    public static BaseDAO getInstance() {
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
    public void addUser(User user) throws DAOException {
        String emailValue = user.getEmailValue();
        String usernameValue = user.getUsernameValue();
        String passwordValue = user.getPasswordValue();

        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            if (match(emailValue, usernameValue)) {
                throw new DAOException(emailValue + " " + usernameValue + " already exist");
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
    public String takeUserType(String usernameValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
    @Override
    public String takeUsername(String emailValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
    @Override
    public void changePassword(User user) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }

    @Override
    public boolean isUsernameExist(String usernameValue) throws DAOException {
        throw new DAOException("NotSupportedMethod");
    }
}
