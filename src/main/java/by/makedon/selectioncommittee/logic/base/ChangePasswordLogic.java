package by.makedon.selectioncommittee.logic.base;

import by.makedon.selectioncommittee.dao.base.BaseDAO;
import by.makedon.selectioncommittee.dao.base.BaseDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.validator.UserValidator;
import com.sun.istack.internal.NotNull;

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

        BaseDAO dao = BaseDAOImpl.getInstance();
        try {
            dao.changePasswordByUsername(usernameValue, password1Value);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
