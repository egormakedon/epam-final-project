package by.makedon.selectioncommittee.dao.admin;

import by.makedon.selectioncommittee.dao.DAO;
import by.makedon.selectioncommittee.entity.enrolleestate.EnrolleeState;
import by.makedon.selectioncommittee.exception.DAOException;

import java.util.Map;
import java.util.Set;

public interface AdminDAO extends DAO {
    Set<EnrolleeState> takeEnrolleeStateSet() throws DAOException;
    Map<Long,Integer> takeSpecialityIdNumberOfSeatsMap() throws DAOException;
    void refreshStatement(Set<EnrolleeState> enrolleeStateSet) throws DAOException;
    void resetStatement() throws DAOException;
    void deleteEnrolleeFormByUsername(String usernameValue) throws DAOException;
    void deleteUserByUsername(String usernameValue) throws DAOException;
    boolean isStatementInProcess() throws DAOException;
    void changeNumberOfSeats(String specialityValue, String numberOfSeatsValue) throws DAOException;
}
