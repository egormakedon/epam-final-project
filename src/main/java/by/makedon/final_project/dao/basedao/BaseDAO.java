package by.makedon.final_project.dao.basedao;

import by.makedon.final_project.dao.DAO;
import by.makedon.final_project.entity.User;
import by.makedon.final_project.exception.DAOException;

public interface BaseDAO extends DAO {
    void addUser(User user) throws DAOException;
    boolean match(String param1, String param2) throws DAOException;
    String takeUserType(String usernameValue) throws DAOException;
    String takeUsername(String emailValue) throws DAOException;
    void changePassword(User user) throws DAOException;
    boolean isUsernameExist(String usernameValue) throws DAOException;
}