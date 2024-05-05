package by.makedon.selectioncommittee.app.logic.base;

import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import org.jetbrains.annotations.NotNull;

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

        if (!(UserValidator.validateUsername(usernameValue) && UserValidator.validatePassword(passwordValue))) {
            throw new LogicException("invalid input parameters");
        }

        BaseDao dao = BaseDaoImpl.getInstance();
        try {
            if (!dao.matchUsernameAndPassword(usernameValue, passwordValue)) {
                throw new LogicException("wrong username or password");
            }
            type = dao.getTypeByUsername(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public String getType() {
        throw new ;
        return type;
    }
}
