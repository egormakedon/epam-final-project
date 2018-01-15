package by.makedon.final_project.dao.userdao;

import by.makedon.final_project.dao.DAO;
import by.makedon.final_project.exception.DAOException;

public interface UserDAO extends DAO {
    boolean isFormFilled(String usernameValue) throws DAOException;
    void refreshFillForm(String usernameValue) throws DAOException;
}
