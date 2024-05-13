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

import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE;
import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.USER_NOT_EXIST_WITH_USERNAME;

public class ChangeEmailLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 2;

    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public int getValidParametersSize() {
        return VALID_PARAMETERS_SIZE;
    }

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        String usernameValue = parameters.get(0);
        if (!userValidator.validateUsername(usernameValue)) {
            final String message = String.format(
                INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE, "username", usernameValue);
            throw new ValidationException(message);
        }
        if (!baseDao.matchUsername(usernameValue)) {
            final String message = String.format(USER_NOT_EXIST_WITH_USERNAME, usernameValue);
            throw new ValidationException(message);
        }

        String newEmailValue = parameters.get(1);
        if (!userValidator.validateEmail(newEmailValue)) {
            final String message = String.format(
                INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE, "newEmail", newEmailValue);
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String username = parameters.get(0);
        String newEmail = parameters.get(1);
        userDao.updateEmailByUsername(username, newEmail);
    }
}
