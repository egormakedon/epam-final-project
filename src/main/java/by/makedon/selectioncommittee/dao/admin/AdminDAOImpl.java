package by.makedon.selectioncommittee.dao.admin;

import by.makedon.selectioncommittee.connectionpool.ConnectionPool;
import by.makedon.selectioncommittee.connectionpool.ProxyConnection;
import by.makedon.selectioncommittee.entity.EnrolleeState;
import by.makedon.selectioncommittee.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AdminDAOImpl implements AdminDAO {
    private static final AdminDAO INSTANCE = new AdminDAOImpl();
    private AdminDAOImpl(){}
    public static AdminDAO getInstance() {
        return INSTANCE;
    }

    private static final String SCORE = "score";
    private static final String ENROLLEE_ID = "enrolleeId";
    private static final String SPECIALITY_ID = "specialityId";
    private static final String NUMBER_OF_SEATS = "numberOfSeats";
    private static final String STATEMENT = "statement";
    private static final String STATEMENT_IN_PROCESS = "В процессе";

    private static final String SQL_DELETE_ENROLLEE_BY_USERNAME = "DELETE FROM enrollee e WHERE e.e_id IN (SELECT u.e_id FROM user u WHERE u.username=?);";
    private static final String SQL_DELETE_USER_BY_USERNAME = "DELETE FROM user WHERE username=?";
    private static final String SQL_UPDATE_RESET_STATEMENT = "UPDATE enrollee SET statement='В процессе';";
    private static final String SQL_SELECT_ALL_ENROLLEE_E_ID_S_ID_SCORE = "SELECT e_id enrolleeId, s_id specialityId, erussian_lang+belorussian_lang+physics+math+chemistry+biology+foreign_lang+history_of_belarus+social_studies+geography+history+certificate score FROM enrollee;";
    private static final String SQL_SELECT_ALL_SPECIALITY_ID_NUMBER_OF_SEATS = "SELECT s_id specialityId, number_of_seats numberOfSeats FROM speciality;";
    private static final String SQL_UPDATE_STATEMENT_BY_ENROLLEE_ID = "UPDATE enrollee SET statement=? WHERE e_id=?";
    private static final String SQL_UPDATE_NUMBER_OF_SEATS_BY_SPECIALITY = "UPDATE speciality SET number_of_seats=? WHERE s_name=?";
    private static final String SQL_SELECT_STATEMENT = "SELECT statement FROM enrollee LIMIT 1";

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
    public void resetStatement() throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_RESET_STATEMENT);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
    @Override
    public Set<EnrolleeState> takeAllEnrolleeStates() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ENROLLEE_E_ID_S_ID_SCORE);

            Set<EnrolleeState> stateSet = new HashSet<EnrolleeState>();
            while (resultSet.next()) {
                int scoreValue = Integer.parseInt(resultSet.getString(SCORE));
                long enrolleeIdValue = Long.parseLong(resultSet.getString(ENROLLEE_ID));
                long specialityIdValue = Long.parseLong(resultSet.getString(SPECIALITY_ID));
                EnrolleeState enrolleeState = new EnrolleeState(enrolleeIdValue, specialityIdValue, scoreValue);
                stateSet.add(enrolleeState);
            }
            if (stateSet.isEmpty()) {
                throw new DAOException("list of enrollee is empty");
            }
            return stateSet;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
    @Override
    public Map<Long, Integer> takeSpecialityIdWithNumberOfSeats() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_SPECIALITY_ID_NUMBER_OF_SEATS);
            Map<Long,Integer> specialityIdNumberOfSeatsMap = new HashMap<Long,Integer>();
            while (resultSet.next()) {
                long specialityIdValue = resultSet.getLong(SPECIALITY_ID);
                int numberOfSeatsValue = resultSet.getInt(NUMBER_OF_SEATS);
                specialityIdNumberOfSeatsMap.put(specialityIdValue, numberOfSeatsValue);
            }
            if (specialityIdNumberOfSeatsMap.isEmpty()) {
                throw new DAOException("list of speciality is empty");
            }
            return specialityIdNumberOfSeatsMap;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
    @Override
    public void refreshStatement(Set<EnrolleeState> enrolleeStateSet) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_STATEMENT_BY_ENROLLEE_ID);

            Iterator<EnrolleeState> enrolleeStateIterator = enrolleeStateSet.iterator();
            while (enrolleeStateIterator.hasNext()) {
                EnrolleeState enrolleeState = enrolleeStateIterator.next();
                String statementValue = enrolleeState.getStatement();
                long enrolleeIdValue = enrolleeState.getEnrolleeId();
                statement.setString(1, statementValue);
                statement.setLong(2, enrolleeIdValue);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }

    }
    @Override
    public void changeNumberOfSeats(String specialityValue, String numberOfSeatsValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_NUMBER_OF_SEATS_BY_SPECIALITY);
            statement.setInt(1, Integer.valueOf(numberOfSeatsValue));
            statement.setString(2, specialityValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
    @Override
    public boolean isEnrolleeStatementInProcess() throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_STATEMENT);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String statementValue = resultSet.getString(STATEMENT);
                return statementValue.equals(STATEMENT_IN_PROCESS);
            }
            return false;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}