package by.makedon.selectioncommittee.app.logic.base;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.entity.User;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AcceptRegistrationLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 3;
    private final BaseDao baseDao = BaseDaoImpl.getInstance();

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        if (parameters.size() != VALID_PARAMETERS_SIZE) {
            final String message = String.format(
                "Invalid input parameters size: expected=`%d`, actual=`%d`", VALID_PARAMETERS_SIZE, parameters.size());
            throw new ValidationException(message);
        }

        String emailValue = parameters.get(0);
        String usernameValue = parameters.get(1);

        if (baseDao.matchEmailAndUsername(emailValue, usernameValue)) {
            final String message = String.format(
                "Such user with email:`%s`, username:`%s` already exists", emailValue, usernameValue);
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String email = parameters.get(0);
        String username = parameters.get(1);
        String password = parameters.get(2);

        User user = User
            .builder()
            .email(email)
            .username(username)
            .password(password)
            .build();
        baseDao.insertUser(user);
    }
}
