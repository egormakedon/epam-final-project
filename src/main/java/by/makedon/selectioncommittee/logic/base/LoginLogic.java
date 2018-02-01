package by.makedon.selectioncommittee.logic.base;

import by.makedon.selectioncommittee.dao.base.BaseDAO;
import by.makedon.selectioncommittee.dao.base.BaseDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.validator.UserValidator;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class LoginLogic implements Logic {
    private static final int LIST_SIZE = 2;

    private String type;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);
        String passwordValue = parameters.get(1);

        if (!UserValidator.validateUsername(usernameValue) && UserValidator.validatePassword(passwordValue)) {
            throw new LogicException("invalid input parameters");
        }

        BaseDAO dao = BaseDAOImpl.getInstance();
        try {
            if (!dao.matchUsernamePassword(usernameValue, passwordValue)) {
                throw new LogicException("wrong username or password");
            }
            type = dao.takeTypeByUsername(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public String getType() {
        return type;
    }
}
