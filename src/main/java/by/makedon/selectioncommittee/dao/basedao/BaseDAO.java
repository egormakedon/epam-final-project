package by.makedon.selectioncommittee.dao.basedao;

import by.makedon.selectioncommittee.dao.DAO;
import by.makedon.selectioncommittee.entity.User;
import by.makedon.selectioncommittee.exception.DAOException;

public interface BaseDAO extends DAO {
    void addUser(User user) throws DAOException;
    boolean match(String param1, String param2) throws DAOException;
    String takeUserType(String usernameValue) throws DAOException;
    String takeUsername(String emailValue) throws DAOException;
    void changePassword(User user) throws DAOException;
    boolean isUsernameExist(String usernameValue) throws DAOException;
}