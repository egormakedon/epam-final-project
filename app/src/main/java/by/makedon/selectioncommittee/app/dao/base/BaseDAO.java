package by.makedon.selectioncommittee.app.dao.base;

import by.makedon.selectioncommittee.app.entity.user.User;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.dao.DAO;

public interface BaseDAO extends DAO {
    void addUser(User user) throws DAOException;
    boolean matchUsernamePassword(String usernameValue, String passwordValue) throws DAOException;
    boolean matchEmailUsername(String emailValue, String usernameValue) throws DAOException;
    String takeTypeByUsername(String usernameValue) throws DAOException;
    String takeUsernameByEmail(String emailValue) throws DAOException;
    void changePasswordByUsername(String usernameValue, String passwordValue) throws DAOException;
    boolean matchUsername(String usernameValue) throws DAOException;
}
