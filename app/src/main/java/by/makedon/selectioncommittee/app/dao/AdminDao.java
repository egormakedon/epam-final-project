package by.makedon.selectioncommittee.app.dao;

import by.makedon.selectioncommittee.app.entity.AdminEnrolleeForm;
import by.makedon.selectioncommittee.app.entity.EnrolleeState;
import by.makedon.selectioncommittee.app.entity.SpecialityState;
import by.makedon.selectioncommittee.common.dao.Dao;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdminDao extends Dao<Object, Object> {
    String FIELD_SCORE = "score";
    String FIELD_ENROLLEE_ID = "enrolleeId";
    String FIELD_SPECIALITY_ID = "specialityId";
    String FIELD_DATE = "date";
    String FIELD_NUMBER_OF_SEATS = "numberOfSeats";
    String FIELD_STATEMENT = "statement";
    String FIELD_USERNAME = "username";
    String FIELD_FILLED_DOCUMENTS = "filledDocuments";
    String FIELD_EMAIl = "email";
    String FIELD_RESULT = "result";
    String FIELD_PASSPORT_ID = "passportId";
    String FIELD_COUNTRY_DOMEN = "countryDomen";
    String FIELD_SURNAME = "surname";
    String FIELD_NAME = "name";
    String FIELD_SECOND_NAME = "secondName";
    String FIELD_PHONE = "phone";
    String FIELD_SPECIALITY = "speciality";

    String SQL_SELECT_ALL_ENROLLEE_ID_SPECIALITY_ID_SCORE = "SELECT e_id enrolleeId, s_id specialityId, date, " +
        "russian_lang+belorussian_lang+physics+math+chemistry+biology+foreign_lang+history_of_belarus+social_studies+geography+" +
        "history+certificate score FROM enrollee";
    String SQL_SELECT_ALL_SPECIALITY_ID_NUMBER_OF_SEATS = "SELECT s_id specialityId, number_of_seats numberOfSeats " +
        "FROM speciality";
    String SQL_UPDATE_STATEMENT_BY_ENROLLEE_ID = "UPDATE enrollee SET statement=? WHERE e_id=?";
    String SQL_DELETE_ENROLLEE_BY_USERNAME = "DELETE FROM enrollee WHERE enrollee.e_id = " +
        "(SELECT user.e_id FROM user WHERE username=?)";
    String SQL_DELETE_USER_BY_USERNAME = "DELETE FROM user WHERE username=?";
    String SQL_UPDATE_RESET_STATEMENT = "UPDATE enrollee SET statement='В процессе'";
    String SQL_UPDATE_NUMBER_OF_SEATS_BY_SPECIALITY = "UPDATE speciality SET number_of_seats=? WHERE s_name=?";
    String SQL_SELECT_FIRST_STATEMENT = "SELECT statement FROM enrollee LIMIT 1";
    String SQL_SELECT_ADMIN_ENROLLEE_FORMS = "SELECT u.username username, e.passport_id passportId, " +
        "e.country_domen countryDomen, e.surname surname, e.name name, e.second_name secondName, e.phone phone, s.s_name speciality, " +
        "russian_lang+belorussian_lang+physics+math+chemistry+biology+foreign_lang+history_of_belarus+social_studies+" +
        "geography+history+certificate score FROM user u INNER JOIN enrollee e ON u.e_id = e.e_id " +
        "INNER JOIN speciality s ON e.s_id = s.s_id ORDER BY speciality ASC, score DESC";
    String SQL_SELECT_SPECIALITY_STATES = "SELECT s.s_name speciality, s.number_of_seats numberOfSeats, " +
        "(SELECT COUNT(e.e_id) FROM enrollee e WHERE s.s_id = e.s_id GROUP BY s.s_id) filledDocuments FROM speciality s";
    String SQL_SELECT_ALL_EMAIL_IS_NULL_E_ID = "SELECT email, isNull(e_id) result from user";

    Set<EnrolleeState> getEnrolleeStates() throws DaoException;
    Map<Long, Integer> getSpecialityIdToNumberOfSeatsMap() throws DaoException;
    void refreshStatement(@NotNull Set<EnrolleeState> enrolleeStateSet) throws DaoException;
    void resetStatement() throws DaoException;
    void deleteEnrolleeFormByUsername(@NotNull String usernameValue) throws DaoException;
    void deleteUserByUsername(@NotNull String usernameValue) throws DaoException;
    boolean isStatementInProcess() throws DaoException;
    void updateNumberOfSeatsBy(@NotNull String specialityValue, int numberOfSeatsValue) throws DaoException;
    List<AdminEnrolleeForm> getAdminEnrolleeFormList() throws DaoException;
    List<SpecialityState> getSpecialityStateList() throws DaoException;
    List<String> getUserEmailList() throws DaoException;
}
