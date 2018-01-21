package by.makedon.final_project.dao.admindao;

import by.makedon.final_project.dao.DAO;
import by.makedon.final_project.exception.DAOException;

public interface AdminDAO extends DAO {
    void deleteUser(String username) throws DAOException;
}
