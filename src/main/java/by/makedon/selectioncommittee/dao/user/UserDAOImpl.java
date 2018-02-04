package by.makedon.selectioncommittee.dao.user;

import by.makedon.selectioncommittee.connectionpool.ConnectionPool;
import by.makedon.selectioncommittee.connectionpool.ProxyConnection;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeForm;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeFormCriteria;
import by.makedon.selectioncommittee.exception.DAOException;
import com.sun.istack.internal.NotNull;
import org.apache.logging.log4j.Level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public final class UserDAOImpl implements UserDAO {
    private static final UserDAO INSTANCE;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    static {
        INSTANCE = new UserDAOImpl();
        instanceCreated.set(true);
    }
    private UserDAOImpl(){
        LOGGER.log(Level.FATAL, "Tried to create singleton object with reflection api");
        throw new RuntimeException("Tried to create singleton object with reflection api");
    }
    public static UserDAO getInstance() {
        return INSTANCE;
    }

    private static final String STATEMENT = "statement";
    private static final String STATEMENT_IN_PROCESS = "В процессе";
    private static final String RESULT = "result";
    private static final String SPECIALITY_ID = "specialityId";
    private static final String ENROLLEE_ID = "enrolleeId";
    private static final String EMAIL = "email";

    private static final String SQL_SELECT_FIRST_STATEMENT = "SELECT statement FROM enrollee LIMIT 1";
    private static final String SQL_SELECT_IS_NULL_E_ID_BY_USERNAME = "SELECT isNull(e_id) result FROM user WHERE username=?";
    private static final String SQL_SELECT_S_ID_BY_UNIVERSITY_FACULTY_SPECIALITY = "SELECT s.s_id specialityId FROM university u " +
            "INNER JOIN faculty f ON u.u_id = f.u_id " +
            "INNER JOIN speciality s ON f.f_id = s.f_id " +
            "WHERE u_name=? AND f_name=? AND s_name=?";
    private static final String SQL_INSERT_ENROLLEE_FORM = "INSERT INTO enrollee(passport_id,country_domen,s_id,surname,name,second_name," +
            "phone,russian_lang,belorussian_lang,physics,math,chemistry,biology,foreign_lang,history_of_belarus,social_studies,geography," +
            "history,certificate,date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_SELECT_E_ID_BY_PASSPORT_ID_COUNTRY_DOMEN = "SELECT e_id enrolleeId " +
            "FROM enrollee " +
            "WHERE passport_id=? AND country_domen=?";
    private static final String SQL_UPDATE_E_ID_IN_USER = "UPDATE user SET e_id=? WHERE username=?";
    private static final String SQL_UPDATE_RESET_ENROLLEE_FORM_BY_USERNAME = "DELETE FROM enrollee WHERE enrollee.e_id IN " +
            "(SELECT u.e_id FROM user u WHERE username=?)";
    private static final String SQL_SELECT_EMAIL_BY_USERNAME = "SELECT email FROM user WHERE username=?";

    ////////////////////////

    private static final String SQL_SELECT_ENROLLE_DATA = "SELECT u.u_name UNIVERSITY, f.f_name FACULTY, s.s_name SPECIALITY," +
            " e.passport_id PASSPORTID, e.country_domen COUNTRYDOMEN," +
            " e.surname SURNAME, e.name NAME, e.second_name SECONDNAME, e.phone PHONE, e.statement STATEMENT, e.russian_lang RUSSIANLANG, " +
            " e.belorussian_lang BELORUSSIANLANG, e.physics PHYSICS, e.math MATH, e.chemistry CHEMISTRY, e.biology BIOLOGY," +
            " e.foreign_lang FOREIGNLANG, e.history_of_belarus HISTORYOFBELARUS, e.social_studies SOCIALSTUDIES, e.geography GEOGRAPHY," +
            " e.history HISTORY, e.certificate CERTIFICATE FROM university u INNER JOIN faculty f ON u.u_id = f.u_id" +
            " INNER JOIN speciality s ON f.f_id = s.f_id INNER JOIN enrollee e ON s.s_id = e.s_id INNER JOIN user ON e.e_id = user.e_id" +
            " WHERE user.username=?;";
    private static final String SQL_UPDATE_CHANGE_EMAIL_BY_USERNAME = "UPDATE user SET email=? WHERE username=?";
    private static final String SQL_UPDATE_CHANGE_USERNAME = "UPDATE user SET username=? WHERE username=?";

    //////////////////////

    @Override
    public boolean couldChangeForm() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_FIRST_STATEMENT);
            if (resultSet.next()) {
                String statementValue = resultSet.getString(STATEMENT);
                return statementValue.equals(STATEMENT_IN_PROCESS);
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean isUserEnrolleeFormAdded(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_IS_NULL_E_ID_BY_USERNAME);
            statement.setString(1,usernameValue);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                byte value = Byte.parseByte(resultSet.getString(RESULT));
                return value == 0;
            } else {
                throw new DAOException("this user doesn't exist");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void addForm(String usernameValue, @NotNull EnrolleeForm enrolleeForm) throws DAOException {
        addEnrolleeForm(enrolleeForm);

        EnrolleeForm.EnrolleeInfo enrolleeInfo = enrolleeForm.getEnrolleInfo();
        String passportID = enrolleeInfo.getPassportId();
        String countryDomen = enrolleeInfo.getCountryDomen();
        long enrolleeID = takeEnrolleeID(passportID, countryDomen);

        addEnrolleeIdToUser(usernameValue, enrolleeID);
    }

    @Override
    public void resetForm(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_RESET_ENROLLEE_FORM_BY_USERNAME);
            statement.setString(1, usernameValue);

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new DAOException("form doesn't exist");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public String takeEmailByUsername(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_EMAIL_BY_USERNAME);
            statement.setString(1, usernameValue);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(EMAIL);
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
    public Object clone() throws CloneNotSupportedException {
        LOGGER.log(Level.ERROR, "Tried to clone singleton object");
        throw new CloneNotSupportedException("Tried to clone singleton object");
    }

    private void addEnrolleeForm(@NotNull EnrolleeForm enrolleeForm) throws DAOException {
        EnrolleeForm.UniversityInfo universityInfo = enrolleeForm.getUniversityInfo();
        String universityValue = universityInfo.getUniversity();
        String facultyValue = universityInfo.getFaculty();
        String specialityValue = universityInfo.getSpeciality();
        long specialityID = takeSpecialityID(universityValue, facultyValue, specialityValue);

        EnrolleeForm.EnrolleeInfo enrolleeInfo = enrolleeForm.getEnrolleInfo();
        String passportID = enrolleeInfo.getPassportId();
        String countryDomen = enrolleeInfo.getCountryDomen();
        String surname = enrolleeInfo.getSurname();
        String name = enrolleeInfo.getName();
        String secondName = enrolleeInfo.getSecondName();
        String phone = enrolleeInfo.getPhone();
        String date = enrolleeInfo.getDate();

        EnrolleeForm.EnrolleeMark enrolleeMark = enrolleeForm.getEnrolleeMark();
        byte russianLangValue = enrolleeMark.getRussianLang();
        byte belorussianLangValue = enrolleeMark.getBelorussianLang();
        byte physicsValue = enrolleeMark.getPhysics();
        byte mathValue = enrolleeMark.getMath();
        byte chemistryValue = enrolleeMark.getChemistry();
        byte biologyValue = enrolleeMark.getBiology();
        byte foreignLangValue = enrolleeMark.getForeignLang();
        byte historyOfBelarusValue = enrolleeMark.getHistoryOfBelarus();
        byte socialStudiesValue = enrolleeMark.getSocialStudies();
        byte geographyValue = enrolleeMark.getGeography();
        byte historyValue = enrolleeMark.getHistory();
        byte certificateValue = enrolleeMark.getCertificate();

        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_ENROLLEE_FORM);
            statement.setString(1, passportID);
            statement.setString(2, countryDomen);
            statement.setLong(3, specialityID);
            statement.setString(4, surname);
            statement.setString(5, name);
            statement.setString(6, secondName);
            statement.setString(7, phone);
            statement.setByte(8, russianLangValue);
            statement.setByte(9, belorussianLangValue);
            statement.setByte(10, physicsValue);
            statement.setByte(11, mathValue);
            statement.setByte(12, chemistryValue);
            statement.setByte(13, biologyValue);
            statement.setByte(14, foreignLangValue);
            statement.setByte(15, historyOfBelarusValue);
            statement.setByte(16, socialStudiesValue);
            statement.setByte(17, geographyValue);
            statement.setByte(18, historyValue);
            statement.setByte(19, certificateValue);
            statement.setString(20, date);

            int row = statement.executeUpdate();
            if (row == 0) {
                throw new DAOException("form hasn't added");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    private long takeSpecialityID(String universityValue, String facultyValue, String specialityValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_S_ID_BY_UNIVERSITY_FACULTY_SPECIALITY);
            statement.setString(1, universityValue);
            statement.setString(2, facultyValue);
            statement.setString(3, specialityValue);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(SPECIALITY_ID);
            } else {
                throw new DAOException("speciality doesn't exist");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    private long takeEnrolleeID(String passportID, String countryDomen) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_E_ID_BY_PASSPORT_ID_COUNTRY_DOMEN);
            statement.setString(1, passportID);
            statement.setString(2, countryDomen);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(ENROLLEE_ID);
            } else {
                throw new DAOException("form doesn't exist");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    private void addEnrolleeIdToUser(String usernameValue, long enrolleeID) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_E_ID_IN_USER);
            statement.setLong(1, enrolleeID);
            statement.setString(2, usernameValue);

            int row = statement.executeUpdate();
            if (row == 0) {
                throw new DAOException("user doesn't exist");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    public EnrolleeForm takeEnrollee(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;String s = "1";
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ENROLLE_DATA);
            statement.setString(1, usernameValue);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String universityValue = resultSet.getString(EnrolleeFormCriteria.UNIVERSITY.toString());
                String facultyValue = resultSet.getString(EnrolleeFormCriteria.FACULTY.toString());
                String specialityValue = resultSet.getString(EnrolleeFormCriteria.SPECIALITY.toString());
                String nameValue = resultSet.getString(EnrolleeFormCriteria.NAME.toString());
                String surnameValue = resultSet.getString(EnrolleeFormCriteria.SURNAME.toString());
                String secondNameValue = resultSet.getString(EnrolleeFormCriteria.SECONDNAME.toString());
                String passportIdValue = resultSet.getString(EnrolleeFormCriteria.PASSPORTID.toString());
                String countryDomenValue = resultSet.getString(EnrolleeFormCriteria.COUNTRYDOMEN.toString());
                String phoneValue = resultSet.getString(EnrolleeFormCriteria.PHONE.toString());
                String russianLangValue = resultSet.getString(EnrolleeFormCriteria.RUSSIANLANG.toString());
                String belorussianLangValue = resultSet.getString(EnrolleeFormCriteria.BELORUSSIANLANG.toString());
                String physicsValue = resultSet.getString(EnrolleeFormCriteria.PHYSICS.toString());
                String mathValue = resultSet.getString(EnrolleeFormCriteria.MATH.toString());
                String chemistryValue = resultSet.getString(EnrolleeFormCriteria.CHEMISTRY.toString());
                String biologyValue = resultSet.getString(EnrolleeFormCriteria.BIOLOGY.toString());
                String foreignLangValue = resultSet.getString(EnrolleeFormCriteria.FOREIGNLANG.toString());
                String historyOfBelarusValue = resultSet.getString(EnrolleeFormCriteria.HISTORYOFBELARUS.toString());
                String socialStudiesValue = resultSet.getString(EnrolleeFormCriteria.SOCIALSTUDIES.toString());
                String geographyValue = resultSet.getString(EnrolleeFormCriteria.GEOGRAPHY.toString());
                String historyValue = resultSet.getString(EnrolleeFormCriteria.HISTORY.toString());
                String certificateValue = resultSet.getString(EnrolleeFormCriteria.CERTIFICATE.toString());
                //String statementValue = resultSet.getString(EnrolleeFormCriteria.STATEMENT.toString());

                Map<EnrolleeFormCriteria, String> parameters = new HashMap<EnrolleeFormCriteria, String>();
                parameters.put(EnrolleeFormCriteria.UNIVERSITY, universityValue);
                parameters.put(EnrolleeFormCriteria.FACULTY, facultyValue);
                parameters.put(EnrolleeFormCriteria.SPECIALITY, specialityValue);
                parameters.put(EnrolleeFormCriteria.NAME, nameValue);
                parameters.put(EnrolleeFormCriteria.SURNAME, surnameValue);
                parameters.put(EnrolleeFormCriteria.SECONDNAME, secondNameValue);
                parameters.put(EnrolleeFormCriteria.PASSPORTID, passportIdValue);
                parameters.put(EnrolleeFormCriteria.COUNTRYDOMEN, countryDomenValue);
                parameters.put(EnrolleeFormCriteria.PHONE, phoneValue);
                parameters.put(EnrolleeFormCriteria.RUSSIANLANG, russianLangValue);
                parameters.put(EnrolleeFormCriteria.BELORUSSIANLANG, belorussianLangValue);
                parameters.put(EnrolleeFormCriteria.PHYSICS, physicsValue);
                parameters.put(EnrolleeFormCriteria.MATH, mathValue);
                parameters.put(EnrolleeFormCriteria.CHEMISTRY, chemistryValue);
                parameters.put(EnrolleeFormCriteria.BIOLOGY, biologyValue);
                parameters.put(EnrolleeFormCriteria.FOREIGNLANG, foreignLangValue);
                parameters.put(EnrolleeFormCriteria.HISTORYOFBELARUS, historyOfBelarusValue);
                parameters.put(EnrolleeFormCriteria.SOCIALSTUDIES, socialStudiesValue);
                parameters.put(EnrolleeFormCriteria.GEOGRAPHY, geographyValue);
                parameters.put(EnrolleeFormCriteria.HISTORY, historyValue);
                parameters.put(EnrolleeFormCriteria.CERTIFICATE, certificateValue);
                //parameters.put(EnrolleeFormCriteria.STATEMENT, statementValue);

                EnrolleeForm enrolleeForm = new EnrolleeForm(parameters);
                return enrolleeForm;
            } else {
                throw new DAOException("enrollee form hasn't found");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void changeEmail(String usernameValue, String newEmailValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_CHANGE_EMAIL_BY_USERNAME);
            statement.setString(1,newEmailValue);
            statement.setString(2,usernameValue);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean changeUsername(String usernameValue, String newUsernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_CHANGE_USERNAME);
            statement.setString(1, newUsernameValue);
            statement.setString(2, usernameValue);
            int count = statement.executeUpdate();
            return count == 1;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}
