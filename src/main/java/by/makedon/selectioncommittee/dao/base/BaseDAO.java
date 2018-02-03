package by.makedon.selectioncommittee.dao.base;

import by.makedon.selectioncommittee.dao.DAO;
import by.makedon.selectioncommittee.entity.user.User;
import by.makedon.selectioncommittee.exception.DAOException;

public interface BaseDAO extends DAO {
    void addUser(User user) throws DAOException;
    boolean matchUsernamePassword(String usernameValue, String passwordValue) throws DAOException;
    boolean matchEmailUsername(String emailValue, String usernameValue) throws DAOException;
    String takeTypeByUsername(String usernameValue) throws DAOException;
    String takeUsernameByEmail(String emailValue) throws DAOException;
    void changePasswordByUsername(String usernameValue, String passwordValue) throws DAOException;
    boolean matchUsername(String usernameValue) throws DAOException;
}