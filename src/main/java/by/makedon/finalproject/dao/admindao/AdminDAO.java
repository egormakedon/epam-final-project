package by.makedon.finalproject.dao.admindao;

import by.makedon.finalproject.dao.DAO;
import by.makedon.finalproject.entity.EnrolleeState;
import by.makedon.finalproject.exception.DAOException;

import java.util.Map;
import java.util.Set;

public interface AdminDAO extends DAO {
    void deleteUser(String usernameValue) throws DAOException;
    void resetStatement() throws DAOException;
    Set<EnrolleeState> takeAllEnrolleeStates() throws DAOException;
    Map<Long,Integer> takeSpecialityIdWithNumberOfSeats() throws DAOException;
    void refreshStatement(Set<EnrolleeState> enrolleeStateSet) throws DAOException;
}
