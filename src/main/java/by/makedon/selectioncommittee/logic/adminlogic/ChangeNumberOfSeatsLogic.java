package by.makedon.selectioncommittee.logic.adminlogic;

import by.makedon.selectioncommittee.dao.admindao.AdminDAO;
import by.makedon.selectioncommittee.dao.admindao.AdminDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.validator.NumberOfSeatsValidator;

public class ChangeNumberOfSeatsLogic {
    public void doAction(String specialityValue, String numberOfSeatsValue) throws LogicException {
        if (!NumberOfSeatsValidator.validate(numberOfSeatsValue)) {
            throw new LogicException("wrong parameter");
        }
        AdminDAO dao = AdminDAOImpl.getInstance();
        try {
            if (dao.isEnrolleeStatementInProcess()) {
                dao.changeNumberOfSeats(specialityValue, numberOfSeatsValue);
            } else {
                throw new LogicException("can't change number of seats: statement is not in process");
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
