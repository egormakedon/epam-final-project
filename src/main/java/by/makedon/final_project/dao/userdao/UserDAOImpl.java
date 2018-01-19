package by.makedon.final_project.dao.userdao;

import by.makedon.final_project.connectionpool.ConnectionPool;
import by.makedon.final_project.connectionpool.ProxyConnection;
import by.makedon.final_project.entity.EnrolleeParameter;
import by.makedon.final_project.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    private static final UserDAO INSTANCE = new UserDAOImpl();
    private UserDAOImpl(){}
    public static UserDAO getInstance() {
        return INSTANCE;
    }

    private static final String RESULT = "result";
    private static final String STATEMENT_IN_PROCESS = "В процессе";

    private static final String SQL_SELECT_IS_NULL_E_ID_BY_USERNAME = "SELECT isNull(e_id) result FROM user WHERE username=?";
    private static final String SQL_SELECT_STATEMENT_BY_USERNAME = "SELECT e.statement statement FROM user u INNER JOIN enrollee e ON u.e_id = e.e_id WHERE username=?";
    private static final String SQL_UPDATE_FROM_BY_USERNAME = "DELETE FROM enrollee WHERE enrollee.e_id IN (SELECT u.e_id FROM user u WHERE username=?);";

    @Override
    public boolean isFormFilled(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_IS_NULL_E_ID_BY_USERNAME);
            statement.setString(1,usernameValue);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int value = Integer.parseInt(resultSet.getString(RESULT));
            return value == 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
    @Override
    public void refreshFillForm(String usernameValue) throws DAOException {
        if (isFormFilled(usernameValue)) {
            if (couldRefreshForm(usernameValue)) {
                refreshForm(usernameValue);
            } else {
                throw new DAOException("you can't refresh form");
            }
        } else {
            throw new DAOException("form is empty yet");
        }
    }



    private boolean couldRefreshForm(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_STATEMENT_BY_USERNAME);
            statement.setString(1, usernameValue);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String statementValue = resultSet.getString(EnrolleeParameter.STATEMENT.getParameter());
            return statementValue.equals(STATEMENT_IN_PROCESS);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
    private void refreshForm(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_FROM_BY_USERNAME);
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
