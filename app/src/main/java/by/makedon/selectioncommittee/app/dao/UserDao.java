package by.makedon.selectioncommittee.app.dao;

import by.makedon.selectioncommittee.app.entity.enrolleeform.EnrolleeForm;
import by.makedon.selectioncommittee.common.dao.Dao;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

public interface UserDao extends Dao<Object, Object> {
    String FIELD_STATEMENT = "statement";
    String FIELD_RESULT = "result";
    String FIELD_SPECIALITY_ID = "specialityId";
    String FIELD_ENROLLEE_ID = "enrolleeId";
    String FIELD_EMAIL = "email";
    String FIELD_USERNAME = "username";
    String FIELD_UNIVERSITY = "university";
    String FIELD_FACULTY = "faculty";
    String FIELD_SPECIALITY = "speciality";
    String FIELD_PASSPORT_ID = "passportId";
    String FIELD_COUNTRY_DOMEN = "countryDomen";
    String FIELD_SURNAME = "surname";
    String FIELD_NAME = "name";
    String FIELD_SECOND_NAME = "secondName";
    String FIELD_PHONE = "phone";
    String FIELD_RUSSIAN_LANG = "russianLang";
    String FIELD_BELORUSSIAN_LANG = "belorussianLang";
    String FIELD_PHYSICS = "physics";
    String FIELD_MATH = "math";
    String FIELD_CHEMISTRY = "chemistry";
    String FIELD_BIOLOGY = "biology";
    String FIELD_FOREIGN_LANG = "foreignLang";
    String FIELD_HISTORY_OF_BELARUS = "historyOfBelarus";
    String FIELD_SOCIAL_STUDIES = "socialStudies";
    String FIELD_GEOGRAPHY = "geography";
    String FIELD_HISTORY = "history";
    String FIELD_CERTIFICATE = "certificate";
    String FIELD_DATE = "date";
    String AUTO_GENERATED_KEY_ENROLLEE_ID = "e_id";

    String SQL_SELECT_FIRST_STATEMENT = "SELECT statement FROM enrollee LIMIT 1";
    String SQL_SELECT_IS_NULL_E_ID_BY_USERNAME = "SELECT isNull(e_id) result FROM user WHERE username=?";
    String SQL_SELECT_S_ID_BY_UNIVERSITY_FACULTY_SPECIALITY = "SELECT s.s_id specialityId FROM university u " +
        "INNER JOIN faculty f ON u.u_id = f.u_id " +
        "INNER JOIN speciality s ON f.f_id = s.f_id " +
        "WHERE u_name=? AND f_name=? AND s_name=?";
    String SQL_INSERT_ENROLLEE_FORM = "INSERT INTO enrollee(passport_id,country_domen,s_id,surname,name,second_name," +
        "phone,russian_lang,belorussian_lang,physics,math,chemistry,biology,foreign_lang,history_of_belarus,social_studies,geography," +
        "history,certificate,date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String SQL_UPDATE_E_ID_IN_USER = "UPDATE user SET e_id=? WHERE username=?";
    String SQL_DELETE_RESET_ENROLLEE_FORM_BY_USERNAME = "DELETE FROM enrollee WHERE enrollee.e_id IN " +
        "(SELECT u.e_id FROM user u WHERE username=?)";
    String SQL_SELECT_EMAIL_BY_USERNAME = "SELECT email FROM user WHERE username=?";
    String SQL_UPDATE_CHANGE_EMAIL_BY_USERNAME = "UPDATE user SET email=? WHERE username=?";
    String SQL_UPDATE_CHANGE_USERNAME = "UPDATE user SET username=? WHERE username=?";
    String SQL_SELECT_ENROLLEE_FORM_BY_USERNAME = "SELECT u.u_name university, f.f_name faculty, s.s_name speciality," +
        " e.passport_id passportId, e.country_domen countryDomen," +
        " e.surname surname, e.name name, e.second_name secondName, e.phone phone, e.russian_lang russianLang, " +
        " e.belorussian_lang belorussianLang, e.physics physics, e.math math, e.chemistry chemistry, e.biology biology," +
        " e.foreign_lang foreignLang, e.history_of_belarus historyOfBelarus, e.social_studies socialStudies, e.geography geography," +
        " e.history history, e.certificate certificate, e.date date FROM university u INNER JOIN faculty f ON u.u_id = f.u_id" +
        " INNER JOIN speciality s ON f.f_id = s.f_id INNER JOIN enrollee e ON s.s_id = e.s_id INNER JOIN user ON e.e_id = user.e_id" +
        " WHERE user.username=?";
    String SQL_SELECT_STATEMENT_BY_USERNAME = "SELECT statement FROM enrollee e INNER JOIN user u ON e.e_id = u.e_id " +
        "WHERE u.username=?";
    String SQL_SELECT_USERNAME_ORDER_BY_SUBJECTS_SCORE_DESC = "SELECT u.username username FROM user u " +
        "INNER JOIN enrollee e ON u.e_id=e.e_id WHERE e.s_id=? ORDER BY russian_lang+belorussian_lang+physics+math+chemistry+biology+" +
        "foreign_lang+history_of_belarus+social_studies+geography+history+certificate DESC";
    String SQL_SELECT_SPECIALITY_ID_BY_USERNAME = "SELECT e.s_id specialityId FROM user u " +
        "INNER JOIN enrollee e ON u.e_id=e.e_id WHERE u.username=?";

    boolean isStatementInProcess() throws DaoException;
    boolean isUserEnrolleeFormInserted(@NotNull String usernameValue) throws DaoException;
    void saveEnrolleeFormByUsername(@NotNull String usernameValue, @NotNull EnrolleeForm enrolleeForm) throws DaoException;
    void resetEnrolleeFormByUsername(@NotNull String usernameValue) throws DaoException;
    String getEmailByUsername(@NotNull String usernameValue) throws DaoException;
    void updateEmailByUsername(@NotNull String usernameValue, @NotNull String newEmailValue) throws DaoException;
    void updateUsername(@NotNull String usernameValue, @NotNull String newUsernameValue) throws DaoException;
    EnrolleeForm getEnrolleeFormByUsername(@NotNull String usernameValue) throws DaoException;
    String getStatementByUsername(@NotNull String usernameValue) throws DaoException;
    long getSpecialityIdByUsername(@NotNull String usernameValue) throws DaoException;
    int getEnrolleePlaceByUsernameAndSpecialityId(@NotNull String usernameValue, long specialityIdValue) throws DaoException;
}
