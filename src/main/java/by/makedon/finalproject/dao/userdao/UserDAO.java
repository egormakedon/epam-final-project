package by.makedon.finalproject.dao.userdao;

import by.makedon.finalproject.dao.DAO;
import by.makedon.finalproject.entity.Enrollee;
import by.makedon.finalproject.exception.DAOException;

public interface UserDAO extends DAO {
    boolean isFormFilled(String usernameValue) throws DAOException;
    void refreshFillForm(String usernameValue) throws DAOException;
    void addForm(String usernameValue, Enrollee enrollee) throws DAOException;
    Enrollee takeEnrollee(String usernameValue) throws DAOException;
    String takeEmail(String usernameValue) throws DAOException;
    void changeEmail(String usernameValue, String newEmailValue) throws DAOException;
    boolean changeUsername(String usernameValue, String newUsernameValue) throws DAOException;
}
