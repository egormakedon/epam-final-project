package by.makedon.final_project.dao.admindao;

import by.makedon.final_project.dao.DAO;
import by.makedon.final_project.entity.EnrolleeState;
import by.makedon.final_project.exception.DAOException;

import java.util.Map;
import java.util.Set;

public interface AdminDAO extends DAO {
    void deleteUser(String usernameValue) throws DAOException;
    void refreshStatement() throws DAOException;
    Set<EnrolleeState> takeAllEnrolleeStates() throws DAOException;
    Map<Long,Integer> takeSpecialityIdWithNumberOfSeats() throws DAOException;
}
