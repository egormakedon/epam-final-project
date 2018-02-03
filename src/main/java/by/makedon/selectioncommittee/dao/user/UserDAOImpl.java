package by.makedon.selectioncommittee.dao.user;

import by.makedon.selectioncommittee.connectionpool.ConnectionPool;
import by.makedon.selectioncommittee.connectionpool.ProxyConnection;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeForm;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeFormCriteria;
import by.makedon.selectioncommittee.exception.DAOException;
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

    private static final String EMAIL = "email";

    private static final String SQL_SELECT_FIRST_STATEMENT = "SELECT statement FROM enrollee LIMIT 1";
    private static final String SQL_SELECT_IS_NULL_E_ID_BY_USERNAME = "SELECT isNull(e_id) result FROM user WHERE username=?";

    private static final String SQL_SELECT_STATEMENT_BY_USERNAME = "SELECT e.statement statement FROM user u INNER JOIN enrollee e ON u.e_id = e.e_id WHERE username=?;";
    private static final String SQL_UPDATE_FORM_BY_USERNAME = "DELETE FROM enrollee WHERE enrollee.e_id IN (SELECT u.e_id FROM user u WHERE username=?);";
    private static final String SQL_SELECT_FIRST_ENROLLEE_STATEMENT = "SELECT statement FROM enrollee LIMIT 1;";
    private static final String SQL_SELECT_S_ID_BY_UNIVERSITY_FACULTY_SPECIALITY = "SELECT s.s_id result FROM university u INNER JOIN faculty f ON u.u_id = f.u_id INNER JOIN speciality s ON f.f_id = s.f_id WHERE u_name=? AND f_name=? AND s_name=?;";
    private static final String SQL_INSERT_ENROLLEE = "INSERT INTO enrollee(passport_id,country_domen,s_id,surname,name,second_name,phone,russian_lang,belorussian_lang,physics,math,chemistry,biology,foreign_lang,history_of_belarus,social_studies,geography,history,certificate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
    private static final String SQL_SELECT_E_ID_BY_PASSPORT_ID_COUNTRY_DOMEN = "SELECT e_id result FROM enrollee WHERE passport_id=? AND country_domen=?";
    private static final String SQL_UPDATE_E_ID_IN_USER = "UPDATE user SET e_id=? WHERE username=?;";
    private static final String SQL_SELECT_ENROLLE_DATA = "SELECT u.u_name UNIVERSITY, f.f_name FACULTY, s.s_name SPECIALITY," +
            " e.passport_id PASSPORTID, e.country_domen COUNTRYDOMEN," +
            " e.surname SURNAME, e.name NAME, e.second_name SECONDNAME, e.phone PHONE, e.statement STATEMENT, e.russian_lang RUSSIANLANG, " +
            " e.belorussian_lang BELORUSSIANLANG, e.physics PHYSICS, e.math MATH, e.chemistry CHEMISTRY, e.biology BIOLOGY," +
            " e.foreign_lang FOREIGNLANG, e.history_of_belarus HISTORYOFBELARUS, e.social_studies SOCIALSTUDIES, e.geography GEOGRAPHY," +
            " e.history HISTORY, e.certificate CERTIFICATE FROM university u INNER JOIN faculty f ON u.u_id = f.u_id" +
            " INNER JOIN speciality s ON f.f_id = s.f_id INNER JOIN enrollee e ON s.s_id = e.s_id INNER JOIN user ON e.e_id = user.e_id" +
            " WHERE user.username=?;";
    private static final String SQL_SELECT_EMAIL_BY_USERNAME = "SELECT email FROM user WHERE username=?";
    private static final String SQL_UPDATE_CHANGE_EMAIL_BY_USERNAME = "UPDATE user SET email=? WHERE username=?";
    private static final String SQL_UPDATE_CHANGE_USERNAME = "UPDATE user SET username=? WHERE username=?";

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
    public void addForm(String usernameValue, EnrolleeForm enrolleeForm) throws DAOException {
        String enrolleeID = addEnrollee(enrolleeForm);
        addEnrolleIdToUser(usernameValue, enrolleeID);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LOGGER.log(Level.ERROR, "Tried to clone singleton object");
        throw new CloneNotSupportedException("Tried to clone singleton object");
    }

    private String addEnrollee(EnrolleeForm enrolleeForm) throws DAOException {
        String specialityID = takeSpecialityID(enrolleeForm);

        EnrolleeForm.EnrolleeInfo enrolleeInfo = enrolleeForm.getEnrolleInfo();
        EnrolleeForm.EnrolleeMark enrolleeMark = enrolleeForm.getEnrolleeMark();

        String passportID = enrolleeInfo.getPassportId();
        String countryDomen = enrolleeInfo.getCountryDomen();
        String surname = enrolleeInfo.getSurname();
        String name = enrolleeInfo.getName();
        String secondName = enrolleeInfo.getSecondName();
        String phone = enrolleeInfo.getPhone();

        String russianLangValue = String.valueOf(enrolleeMark.getRussianLang());
        String belorussianLangValue = String.valueOf(enrolleeMark.getBelorussianLang());
        String physicsValue = String.valueOf(enrolleeMark.getPhysics());
        String mathValue = String.valueOf(enrolleeMark.getMath());
        String chemistryValue = String.valueOf(enrolleeMark.getChemistry());
        String biologyValue = String.valueOf(enrolleeMark.getBiology());
        String foreignLangValue = String.valueOf(enrolleeMark.getForeignLang());
        String historyOfBelarusValue = String.valueOf(enrolleeMark.getHistoryOfBelarus());
        String socialStudiesValue = String.valueOf(enrolleeMark.getSocialStudies());
        String geographyValue = String.valueOf(enrolleeMark.getGeography());
        String historyValue = String.valueOf(enrolleeMark.getHistory());
        String certificateValue = String.valueOf(enrolleeMark.getCertificate());

        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INSERT_ENROLLEE);
            statement.setString(1,passportID);
            statement.setString(2,countryDomen);
            statement.setString(3,specialityID);
            statement.setString(4,surname);
            statement.setString(5,name);
            statement.setString(6,secondName);
            statement.setString(7,phone);
            statement.setString(8,russianLangValue);
            statement.setString(9,belorussianLangValue);
            statement.setString(10,physicsValue);
            statement.setString(11,mathValue);
            statement.setString(12,chemistryValue);
            statement.setString(13,biologyValue);
            statement.setString(14,foreignLangValue);
            statement.setString(15,historyOfBelarusValue);
            statement.setString(16,socialStudiesValue);
            statement.setString(17,geographyValue);
            statement.setString(18,historyValue);
            statement.setString(19,certificateValue);
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

        connection = null;
        statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_E_ID_BY_PASSPORT_ID_COUNTRY_DOMEN);
            statement.setString(1,passportID);
            statement.setString(2,countryDomen);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String enrolleID = resultSet.getString(RESULT);
            return enrolleID;
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
                String statementValue = resultSet.getString(EnrolleeFormCriteria.STATEMENT.toString());

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
                parameters.put(EnrolleeFormCriteria.STATEMENT, statementValue);

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
    public String takeEmail(String usernameValue) throws DAOException {
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
                throw new DAOException("Unknown result, username doesn't exist");
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


    private boolean couldRefreshForm(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_STATEMENT_BY_USERNAME);
            statement.setString(1, usernameValue);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String statementValue = resultSet.getString(EnrolleeFormCriteria.DATE.toString());
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
            statement = connection.prepareStatement(SQL_UPDATE_FORM_BY_USERNAME);
            statement.setString(1, usernameValue);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    private boolean couldAddForm() throws DAOException {
        ProxyConnection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_FIRST_ENROLLEE_STATEMENT);
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

    private void addFormAction(String usernameValue, EnrolleeForm enrolleeForm) throws DAOException {
        String enrolleeID = addEnrollee(enrolleeForm);
        addEnrolleIdToUser(usernameValue, enrolleeID);
    }

    private String takeSpecialityID(EnrolleeForm enrolleeForm) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        EnrolleeForm.UniversityInfo universityInfo = enrolleeForm.getUniversityInfo();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_S_ID_BY_UNIVERSITY_FACULTY_SPECIALITY);
            statement.setString(1,universityInfo.getUniversity());
            statement.setString(2,universityInfo.getFaculty());
            statement.setString(3,universityInfo.getSpeciality());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(RESULT);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }



    private void addEnrolleIdToUser(String usernameValue, String enrolleeID) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_E_ID_IN_USER);
            statement.setString(1, enrolleeID);
            statement.setString(2, usernameValue);
            int row = statement.executeUpdate();
            if (row == 0) {
                throw new DAOException("user e_id hasn't updated");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}
