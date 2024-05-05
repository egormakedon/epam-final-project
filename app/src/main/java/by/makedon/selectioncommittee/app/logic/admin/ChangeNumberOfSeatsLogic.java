package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.validator.NumberOfSeatsValidator;
import org.jetbrains.annotations.NotNull;

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

        AdminDao dao = AdminDaoImpl.getInstance();
        try {
            if (dao.isStatementInProcess()) {
                dao.updateNumberOfSeatsBy(specialityValue, numberOfSeatsValue);
            } else {
                throw new LogicException("can't change number of seats: statement is not in process");
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
