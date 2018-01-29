package by.makedon.finalproject.dao.basedao;

import by.makedon.finalproject.dao.DAO;
import by.makedon.finalproject.entity.User;
import by.makedon.finalproject.exception.DAOException;

public interface BaseDAO extends DAO {
    void addUser(User user) throws DAOException;
    boolean match(String param1, String param2) throws DAOException;
    String takeUserType(String usernameValue) throws DAOException;
    String takeUsername(String emailValue) throws DAOException;
    void changePassword(User user) throws DAOException;
    boolean isUsernameExist(String usernameValue) throws DAOException;
}