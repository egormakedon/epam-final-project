package by.makedon.final_project.dao.admindao;

import by.makedon.final_project.connectionpool.ConnectionPool;
import by.makedon.final_project.connectionpool.ProxyConnection;
import by.makedon.final_project.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO {
    private static final AdminDAO INSTANCE = new AdminDAOImpl();
    private AdminDAOImpl(){}
    public static AdminDAO getInstance() {
        return INSTANCE;
    }

    private static final String SQL_DELETE_ENROLLEE_BY_USERNAME = "DELETE FROM enrollee e WHERE e.e_id IN (SELECT u.e_id FROM user u WHERE u.username=?);";
    private static final String SQL_DELETE_USER_BY_USERNAME = "DELETE FROM user WHERE username=?";

    @Override
    public void deleteUser(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_ENROLLEE_BY_USERNAME);
            statement.setString(1, usernameValue);
            statement.executeUpdate();
            close(statement);
            statement = connection.prepareStatement(SQL_DELETE_USER_BY_USERNAME);
            statement.setString(1, usernameValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}
