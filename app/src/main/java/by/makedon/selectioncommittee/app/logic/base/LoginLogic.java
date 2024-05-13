package by.makedon.selectioncommittee.app.logic.base;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LoginLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 2;

    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    @Getter
    private String type = "";

    @Override
    public int getValidParametersSize() {
        return VALID_PARAMETERS_SIZE;
    }

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        String usernameValue = parameters.get(0);
        String passwordValue = parameters.get(1);

        if (!(userValidator.validateUsername(usernameValue) && userValidator.validatePassword(passwordValue))) {
            final String message = "Invalid input username or password";
            throw new ValidationException(message);
        }

        if (!baseDao.matchUsernameAndPassword(usernameValue, passwordValue)) {
            final String message = "Incorrect username or password";
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        type = "";

        String username = parameters.get(0);
        type = baseDao.getTypeByUsername(username);
    }
}
