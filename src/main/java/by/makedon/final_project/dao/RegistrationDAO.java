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

    private RegistrationDAO(){}

    public static DAO getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isUserExist(String emailValue, String usernameValue) throws DAOException {
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
}
