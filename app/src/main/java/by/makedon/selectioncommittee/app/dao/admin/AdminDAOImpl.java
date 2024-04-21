package by.makedon.selectioncommittee.app.dao.admin;

import by.makedon.selectioncommittee.app.dao.DAO;
import by.makedon.selectioncommittee.app.entity.enrollee.AdminEnrolleeForm;
import by.makedon.selectioncommittee.app.entity.enrollee.EnrolleeFormCriteria;
import by.makedon.selectioncommittee.app.entity.enrollee.EnrolleeState;
import by.makedon.selectioncommittee.app.entity.speciality.SpecialityState;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.connectionpool.ConnectionPool;
import by.makedon.selectioncommittee.connectionpool.ProxyConnection;
import org.jetbrains.annotations.NotNull;
import org.apache.logging.log4j.Level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public final class AdminDAOImpl implements AdminDAO {
    private static final AdminDAO INSTANCE;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    static {
        INSTANCE = new AdminDAOImpl();
        instanceCreated.set(true);
    }
    private AdminDAOImpl() {
        if (instanceCreated.get()) {
            DAO.LOGGER.log(Level.FATAL, "Tried to create singleton object with reflection api");
            throw new RuntimeException("Tried to create singleton object with reflection api");
        }
    }
    public static AdminDAO getInstance() {
        return INSTANCE;
    }

    private static final String SCORE = "score";
    private static final String ENROLLEE_ID = "enrolleeId";
    private static final String SPECIALITY_ID = "specialityId";
    private static final String NUMBER_OF_SEATS = "numberOfSeats";
    private static final String STATEMENT = "statement";
    private static final String STATEMENT_IN_PROCESS = "В процессе";
    private static final String DATE = "date";
    private static final String USERNAME = "username";
    private static final String FILLED_DOCUMENTS = "filledDocuments";
    private static final String EMAIl = "email";
    private static final String RESULT = "result";

    private static final String SQL_SELECT_ALL_ENROLLEE_ID_SPECIALITY_ID_SCORE = "SELECT e_id enrolleeId, s_id specialityId, date, " +
            "russian_lang+belorussian_lang+physics+math+chemistry+biology+foreign_lang+history_of_belarus+social_studies+geography+" +
            "history+certificate score FROM enrollee";
    private static final String SQL_SELECT_ALL_SPECIALITY_ID_NUMBER_OF_SEATS = "SELECT s_id specialityId, number_of_seats numberOfSeats " +
            "FROM speciality";
    private static final String SQL_UPDATE_STATEMENT_BY_ENROLLEE_ID = "UPDATE enrollee SET statement=? WHERE e_id=?";
    private static final String SQL_DELETE_ENROLLEE_BY_USERNAME = "DELETE FROM enrollee WHERE enrollee.e_id = " +
            "(SELECT user.e_id FROM user WHERE username=?)";
    private static final String SQL_DELETE_USER_BY_USERNAME = "DELETE FROM user WHERE username=?";
    private static final String SQL_UPDATE_RESET_STATEMENT = "UPDATE enrollee SET statement='В процессе'";
    private static final String SQL_UPDATE_NUMBER_OF_SEATS_BY_SPECIALITY = "UPDATE speciality SET number_of_seats=? WHERE s_name=?";
    private static final String SQL_SELECT_STATEMENT_FIRST = "SELECT statement FROM enrollee LIMIT 1";
    private static final String SQL_SELECT_ADMIN_ENROLLEE_FORMS = "SELECT u.username username, e.passport_id PASSPORTID, " +
            "e.country_domen COUNTRYDOMEN, e.surname SURNAME, e.name NAME, e.second_name SECONDNAME, e.phone PHONE, s.s_name SPECIALITY, " +
            "russian_lang+belorussian_lang+physics+math+chemistry+biology+foreign_lang+history_of_belarus+social_studies+" +
            "geography+history+certificate score FROM user u INNER JOIN enrollee e ON u.e_id = e.e_id " +
            "INNER JOIN speciality s ON e.s_id = s.s_id ORDER BY SPECIALITY ASC, score DESC";
    private static final String SQL_SELECT_SPECIALITY_STATES = "SELECT s.s_name SPECIALITY, s.number_of_seats numberOfSeats, " +
            "(SELECT COUNT(e.e_id) FROM enrollee e WHERE s.s_id = e.s_id GROUP BY s.s_id) filledDocuments FROM speciality s";
    private static final String SQL_SELECT_ALL_EMAIL_IS_NULL_E_ID = "SELECT email, isNull(e_id) result from user";

    @Override
    public Set<EnrolleeState> takeEnrolleeStateSet() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ENROLLEE_ID_SPECIALITY_ID_SCORE);

            Set<EnrolleeState> enrolleeStateSet = new HashSet<EnrolleeState>();
            while (resultSet.next()) {
                short scoreValue = resultSet.getShort(SCORE);
                long enrolleeIdValue = resultSet.getLong(ENROLLEE_ID);
                long specialityIdValue = resultSet.getLong(SPECIALITY_ID);
                String dateValue = resultSet.getString(DATE);

                EnrolleeState enrolleeState = new EnrolleeState(enrolleeIdValue, specialityIdValue, scoreValue, dateValue);
                enrolleeStateSet.add(enrolleeState);
            }

            if (enrolleeStateSet.isEmpty()) {
                throw new DAOException("enrollee form is empty");
            }
            return enrolleeStateSet;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Map<Long, Integer> takeSpecialityIdNumberOfSeatsMap() throws DAOException {
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
                throw new DAOException("enrollee form is empty");
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
    public void refreshStatement(@NotNull Set<EnrolleeState> enrolleeStateSet) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_STATEMENT_BY_ENROLLEE_ID);

            for (EnrolleeState enrolleeState : enrolleeStateSet) {
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
    public void resetStatement() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(SQL_UPDATE_RESET_STATEMENT);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void deleteEnrolleeFormByUsername(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_ENROLLEE_BY_USERNAME);
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
    public void deleteUserByUsername(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_USER_BY_USERNAME);
            statement.setString(1, usernameValue);
            int rows = statement.executeUpdate();
            if (rows == 0) {
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
    public boolean isStatementInProcess() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_STATEMENT_FIRST);

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

    @Override
    public void changeNumberOfSeats(String specialityValue, String numberOfSeatsValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_NUMBER_OF_SEATS_BY_SPECIALITY);
            statement.setInt(1, Integer.valueOf(numberOfSeatsValue));
            statement.setString(2, specialityValue);

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new DAOException("speciality doesn't exist");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<AdminEnrolleeForm> takeAdminEnrolleeFormList() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ADMIN_ENROLLEE_FORMS);

            List<AdminEnrolleeForm> adminEnrolleeFormList = new ArrayList<AdminEnrolleeForm>();
            while (resultSet.next()) {
                AdminEnrolleeForm adminEnrolleeForm = new AdminEnrolleeForm();
                adminEnrolleeForm.setUsername(resultSet.getString(USERNAME));
                adminEnrolleeForm.setPassportId(resultSet.getString(EnrolleeFormCriteria.PASSPORTID.toString()));
                adminEnrolleeForm.setCountryDomen(resultSet.getString(EnrolleeFormCriteria.COUNTRYDOMEN.toString()));
                adminEnrolleeForm.setSurname(resultSet.getString(EnrolleeFormCriteria.SURNAME.toString()));
                adminEnrolleeForm.setName(resultSet.getString(EnrolleeFormCriteria.NAME.toString()));
                adminEnrolleeForm.setSecondname(resultSet.getString(EnrolleeFormCriteria.SECONDNAME.toString()));
                adminEnrolleeForm.setPhone(resultSet.getString(EnrolleeFormCriteria.PHONE.toString()));
                adminEnrolleeForm.setSpeciality(resultSet.getString(EnrolleeFormCriteria.SPECIALITY.toString()));
                adminEnrolleeForm.setScore(resultSet.getInt(SCORE));
                adminEnrolleeFormList.add(adminEnrolleeForm);
            }

            if (adminEnrolleeFormList.isEmpty()) {
                throw new DAOException("enrollee form is empty");
            }
            return adminEnrolleeFormList;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<SpecialityState> takeSpecialityStateList() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_SPECIALITY_STATES);

            List<SpecialityState> specialityStateList = new ArrayList<SpecialityState>();
            while (resultSet.next()) {
                SpecialityState specialityState = new SpecialityState();
                specialityState.setSpeciality(resultSet.getString(EnrolleeFormCriteria.SPECIALITY.toString()));
                specialityState.setNumberOfSeats(resultSet.getInt(NUMBER_OF_SEATS));
                specialityState.setFilledDocuments(resultSet.getInt(FILLED_DOCUMENTS));
                specialityStateList.add(specialityState);
            }

            if (specialityStateList.isEmpty()) {
                throw new DAOException("no one speciality doesn't exist");
            }
            return specialityStateList;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<String> takeEnrolleeEmailList() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_EMAIL_IS_NULL_E_ID);

            List<String> emailList = new ArrayList<String>();
            while (resultSet.next()) {
                int result = resultSet.getInt(RESULT);
                if (result == 0) {
                    emailList.add(resultSet.getString(EMAIl));
                }
            }
            return emailList;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DAO.LOGGER.log(Level.ERROR, "Tried to clone singleton object");
        throw new CloneNotSupportedException("Tried to clone singleton object");
    }
}
