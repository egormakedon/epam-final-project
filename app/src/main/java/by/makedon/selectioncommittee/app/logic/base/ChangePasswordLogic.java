package by.makedon.selectioncommittee.app.logic.base;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChangePasswordLogic implements Logic {
    private static final int LIST_SIZE = 3;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);
        String password1Value = parameters.get(1);
        String password2Value = parameters.get(2);

        if (!(UserValidator.validatePassword(password1Value) && UserValidator.arePasswordsEqual(password1Value, password2Value))) {
            throw new LogicException("invalid input parameters");
        }

        BaseDao dao = BaseDaoImpl.getInstance();
        try {
            dao.updatePasswordByUsername(usernameValue, password1Value);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
