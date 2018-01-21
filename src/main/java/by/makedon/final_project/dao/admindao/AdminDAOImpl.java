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

    private static final String SCORE = "score";
    private static final String ENROLLEE_ID = "enrolleeId";
    private static final String SPECIALITY_ID = "specialityId";
    private static final String RESULT = "result";

    private static final String SQL_DELETE_ENROLLEE_BY_USERNAME = "DELETE FROM enrollee e WHERE e.e_id IN (SELECT u.e_id FROM user u WHERE u.username=?);";
    private static final String SQL_DELETE_USER_BY_USERNAME = "DELETE FROM user WHERE username=?";
    private static final String SQL_UPDATE_STATEMENT = "UPDATE enrollee SET statement='В процессе';";
    private static final String SQL_SELECT_ALL_ENROLLEE_E_ID_S_ID_SCORE = "SELECT e_id enrolleeId, s_id specialityId, russian_lang+belorussian_lang+physics+math+chemistry+biology+foreign_lang+history_of_belarus+social_studies+geography+history+certificate score FROM enrollee;";

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
    @Override
    public void refreshStatement() throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_STATEMENT);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

}
