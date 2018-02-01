package by.makedon.selectioncommittee.logic.admin;

import by.makedon.selectioncommittee.dao.admin.AdminDAO;
import by.makedon.selectioncommittee.dao.admin.AdminDAOImpl;
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
