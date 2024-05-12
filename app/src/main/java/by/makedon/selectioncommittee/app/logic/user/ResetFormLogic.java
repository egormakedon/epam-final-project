package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResetFormLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 1;

    private final UserDao userDao = UserDaoImpl.getInstance();
    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        if (parameters.size() != VALID_PARAMETERS_SIZE) {
            final String message = String.format(
                "Invalid input parameters size: expected=`%d`, actual=`%d`", VALID_PARAMETERS_SIZE, parameters.size());
            throw new ValidationException(message);
        }

        String usernameValue = parameters.get(0);
        if (!userValidator.validateUsername(usernameValue)) {
            final String message = String.format("Invalid input username parameter: `%s`", usernameValue);
            throw new ValidationException(message);
        }
        if (!baseDao.matchUsername(usernameValue)) {
            final String message = String.format("Such user with username:`%s` does not exist", usernameValue);
            throw new ValidationException(message);
        }
        if (!userDao.isUserEnrolleeFormInserted(usernameValue)) {
            final String message = String.format(
                "Enrollee form for the user: `%s` has not been submitted yet!!", usernameValue);
            throw new ValidationException(message);
        }

        if (!userDao.isStatementInProcess()) {
            throw new ValidationException("Reset enrollee form action is declined: statement is not in process!!");
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String username = parameters.get(0);
        userDao.resetEnrolleeFormByUsername(username);
    }
}
