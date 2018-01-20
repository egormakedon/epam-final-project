package by.makedon.final_project.dao.userdao;

import by.makedon.final_project.connectionpool.ConnectionPool;
import by.makedon.final_project.connectionpool.ProxyConnection;
import by.makedon.final_project.entity.Enrollee;
import by.makedon.final_project.entity.EnrolleeParameter;
import by.makedon.final_project.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UserDAOImpl implements UserDAO {
    private static final UserDAO INSTANCE = new UserDAOImpl();
    private UserDAOImpl(){}
    public static UserDAO getInstance() {
        return INSTANCE;
    }

    private static final String RESULT = "result";
    private static final String STATEMENT = "statement";
    private static final String STATEMENT_IN_PROCESS = "В процессе";

    private static final String SQL_SELECT_IS_NULL_E_ID_BY_USERNAME = "SELECT isNull(e_id) result FROM user WHERE username=?;";
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
    public void addForm(String usernameValue, Enrollee enrollee) throws DAOException {
        if (isFormFilled(usernameValue)) {
            throw new DAOException("form has already filled");
        }
        if (couldAddForm()) {
            addFormAction(usernameValue, enrollee);
        } else {
            throw new DAOException("you can't add form");
        }
    }
    @Override
    public Enrollee takeEnrollee(String usernameValue) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;String s = "1";
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ENROLLE_DATA);
            statement.setString(1, usernameValue);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String universityValue = resultSet.getString(EnrolleeParameter.UNIVERSITY.toString());
                String facultyValue = resultSet.getString(EnrolleeParameter.FACULTY.toString());
                String specialityValue = resultSet.getString(EnrolleeParameter.SPECIALITY.toString());
                String nameValue = resultSet.getString(EnrolleeParameter.NAME.toString());
                String surnameValue = resultSet.getString(EnrolleeParameter.SURNAME.toString());
                String secondNameValue = resultSet.getString(EnrolleeParameter.SECONDNAME.toString());
                String passportIdValue = resultSet.getString(EnrolleeParameter.PASSPORTID.toString());
                String countryDomenValue = resultSet.getString(EnrolleeParameter.COUNTRYDOMEN.toString());
                String phoneValue = resultSet.getString(EnrolleeParameter.PHONE.toString());
                String russianLangValue = resultSet.getString(EnrolleeParameter.RUSSIANLANG.toString());
                String belorussianLangValue = resultSet.getString(EnrolleeParameter.BELORUSSIANLANG.toString());
                String physicsValue = resultSet.getString(EnrolleeParameter.PHYSICS.toString());
                String mathValue = resultSet.getString(EnrolleeParameter.MATH.toString());
                String chemistryValue = resultSet.getString(EnrolleeParameter.CHEMISTRY.toString());
                String biologyValue = resultSet.getString(EnrolleeParameter.BIOLOGY.toString());
                String foreignLangValue = resultSet.getString(EnrolleeParameter.FOREIGNLANG.toString());
                String historyOfBelarusValue = resultSet.getString(EnrolleeParameter.HISTORYOFBELARUS.toString());
                String socialStudiesValue = resultSet.getString(EnrolleeParameter.SOCIALSTUDIES.toString());
                String geographyValue = resultSet.getString(EnrolleeParameter.GEOGRAPHY.toString());
                String historyValue = resultSet.getString(EnrolleeParameter.HISTORY.toString());
                String certificateValue = resultSet.getString(EnrolleeParameter.CERTIFICATE.toString());
                String statementValue = resultSet.getString(EnrolleeParameter.STATEMENT.toString());

                Map<EnrolleeParameter, String> parameters = new HashMap<EnrolleeParameter, String>();
                parameters.put(EnrolleeParameter.UNIVERSITY, universityValue);
                parameters.put(EnrolleeParameter.FACULTY, facultyValue);
                parameters.put(EnrolleeParameter.SPECIALITY, specialityValue);
                parameters.put(EnrolleeParameter.NAME, nameValue);
                parameters.put(EnrolleeParameter.SURNAME, surnameValue);
                parameters.put(EnrolleeParameter.SECONDNAME, secondNameValue);
                parameters.put(EnrolleeParameter.PASSPORTID, passportIdValue);
                parameters.put(EnrolleeParameter.COUNTRYDOMEN, countryDomenValue);
                parameters.put(EnrolleeParameter.PHONE, phoneValue);
                parameters.put(EnrolleeParameter.RUSSIANLANG, russianLangValue);
                parameters.put(EnrolleeParameter.BELORUSSIANLANG, belorussianLangValue);
                parameters.put(EnrolleeParameter.PHYSICS, physicsValue);
                parameters.put(EnrolleeParameter.MATH, mathValue);
                parameters.put(EnrolleeParameter.CHEMISTRY, chemistryValue);
                parameters.put(EnrolleeParameter.BIOLOGY, biologyValue);
                parameters.put(EnrolleeParameter.FOREIGNLANG, foreignLangValue);
                parameters.put(EnrolleeParameter.HISTORYOFBELARUS, historyOfBelarusValue);
                parameters.put(EnrolleeParameter.SOCIALSTUDIES, socialStudiesValue);
                parameters.put(EnrolleeParameter.GEOGRAPHY, geographyValue);
                parameters.put(EnrolleeParameter.HISTORY, historyValue);
                parameters.put(EnrolleeParameter.CERTIFICATE, certificateValue);
                parameters.put(EnrolleeParameter.STATEMENT, statementValue);

                Enrollee enrollee = new Enrollee(parameters);
                return enrollee;
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
    private void addFormAction(String usernameValue, Enrollee enrollee) throws DAOException {
        String enrolleeID = addEnrollee(enrollee);
        addEnrolleIdToUser(usernameValue, enrolleeID);
    }
    private String takeSpecialityID(Enrollee enrollee) throws DAOException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        Enrollee.UniversityInfo universityInfo = enrollee.getUniversityInfo();
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
    private String addEnrollee(Enrollee enrollee) throws DAOException {
        String specialityID = takeSpecialityID(enrollee);

        Enrollee.EnrolleeInfo enrolleeInfo = enrollee.getEnrolleInfo();
        Enrollee.EnrolleeMark enrolleeMark = enrollee.getEnrolleeMark();

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
