package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.NumberOfSeatsValidator;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChangeNumberOfSeatsLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 2;

    private final AdminDao adminDao = AdminDaoImpl.getInstance();
    private final NumberOfSeatsValidator numberOfSeatsValidator = new NumberOfSeatsValidator();

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        if (parameters.size() != VALID_PARAMETERS_SIZE) {
            final String message = String.format(
                "Invalid input parameters size: expected=`%d`, actual=`%d`", VALID_PARAMETERS_SIZE, parameters.size());
            throw new ValidationException(message);
        }

        String numberOfSeatsValue = parameters.get(1);
        if (!numberOfSeatsValidator.validate(numberOfSeatsValue)) {
            final String message = String.format("Invalid input numberOfSeats parameter: `%s`", numberOfSeatsValue);
            throw new ValidationException(message);
        }

        if (!adminDao.isStatementInProcess()) {
            throw new ValidationException("Update number of seats action is declined: statement is not in process!!");
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String speciality = parameters.get(0);
        int numberOfSeats = Integer.parseInt(parameters.get(1));
        adminDao.updateNumberOfSeatsBy(speciality, numberOfSeats);
    }
}
