package by.makedon.selectioncommittee.dao.user;

import by.makedon.selectioncommittee.dao.DAO;
import by.makedon.selectioncommittee.entity.enrollee.EnrolleeForm;
import by.makedon.selectioncommittee.exception.DAOException;

public interface UserDAO extends DAO {
    boolean couldChangeForm() throws DAOException;
    boolean isUserEnrolleeFormAdded(String usernameValue) throws DAOException;
    void addForm(String usernameValue, EnrolleeForm enrolleeForm) throws DAOException;
    void resetForm(String usernameValue) throws DAOException;
    String takeEmailByUsername(String usernameValue) throws DAOException;
    void changeEmail(String usernameValue, String newEmailValue) throws DAOException;
    void changeUsername(String usernameValue, String newUsernameValue) throws DAOException;

    /////////////////////////////
    boolean isFormFilled(String usernameValue) throws DAOException;

    EnrolleeForm takeEnrollee(String usernameValue) throws DAOException;
}