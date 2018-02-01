package by.makedon.selectioncommittee.dao.admin;

import by.makedon.selectioncommittee.dao.DAO;
import by.makedon.selectioncommittee.entity.EnrolleeState;
import by.makedon.selectioncommittee.exception.DAOException;

import java.util.Map;
import java.util.Set;

public interface AdminDAO extends DAO {
    void deleteUser(String usernameValue) throws DAOException;
    void resetStatement() throws DAOException;
    Set<EnrolleeState> takeAllEnrolleeStates() throws DAOException;
    Map<Long,Integer> takeSpecialityIdWithNumberOfSeats() throws DAOException;
    void refreshStatement(Set<EnrolleeState> enrolleeStateSet) throws DAOException;
    void changeNumberOfSeats(String specialityValue, String numberOfSeatsValue) throws DAOException;
    boolean isEnrolleeStatementInProcess() throws DAOException;
}
