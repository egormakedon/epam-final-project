package by.makedon.selectioncommittee.logic.admin;

import by.makedon.selectioncommittee.dao.admin.AdminDAO;
import by.makedon.selectioncommittee.dao.admin.AdminDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.validator.NumberOfSeatsValidator;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class ChangeNumberOfSeatsLogic implements Logic {
    private static final int LIST_SIZE = 2;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String specialityValue = parameters.get(0);
        String numberOfSeatsValue = parameters.get(1);

        if (!NumberOfSeatsValidator.validate(numberOfSeatsValue)) {
            throw new LogicException("invalid input parameter");
        }

        AdminDAO dao = AdminDAOImpl.getInstance();
        try {
            if (dao.isStatementInProcess()) {
                dao.changeNumberOfSeats(specialityValue, numberOfSeatsValue);
            } else {
                throw new LogicException("can't change number of seats: statement is not in process");
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
