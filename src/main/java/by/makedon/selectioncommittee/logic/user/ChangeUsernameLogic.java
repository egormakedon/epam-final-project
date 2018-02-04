package by.makedon.selectioncommittee.logic.user;

import by.makedon.selectioncommittee.dao.base.BaseDAO;
import by.makedon.selectioncommittee.dao.base.BaseDAOImpl;
import by.makedon.selectioncommittee.dao.user.UserDAO;
import by.makedon.selectioncommittee.dao.user.UserDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.validator.UserValidator;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class ChangeUsernameLogic implements Logic {
    private static final int LIST_SIZE = 2;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);
        String newUsernameValue = parameters.get(1);

        if (!UserValidator.validateUsername(newUsernameValue)) {
            throw new LogicException("invalid input parameters");
        }

        BaseDAO baseDAO = BaseDAOImpl.getInstance();
        UserDAO userDAO = UserDAOImpl.getInstance();
        try {
            if (baseDAO.matchUsername(usernameValue)) {
                userDAO.changeUsername(usernameValue, newUsernameValue);
            } else {
                throw new LogicException("user doesn't exist");
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
