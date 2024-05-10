package by.makedon.selectioncommittee.app.logic.base;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChangePasswordLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 3;

    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        if (parameters.size() != VALID_PARAMETERS_SIZE) {
            final String message = String.format(
                "Invalid input parameters size: expected=`%d`, actual=`%d`", VALID_PARAMETERS_SIZE, parameters.size());
            throw new ValidationException(message);
        }

        String password1Value = parameters.get(1);
        String password2Value = parameters.get(2);
        if (!(userValidator.validatePassword(password1Value)
            && userValidator.arePasswordsEqual(password1Value, password2Value))) {
            final String message = "Incorrect input password1 and password2";
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String username = parameters.get(0);
        String password = parameters.get(1);

        baseDao.updatePasswordByUsername(username, password);
    }
}
