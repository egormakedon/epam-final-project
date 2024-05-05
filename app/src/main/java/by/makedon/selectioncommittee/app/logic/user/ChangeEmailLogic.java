package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChangeEmailLogic implements Logic {
    private static final int LIST_SIZE = 2;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);
        String newEmailValue = parameters.get(1);

        if (!UserValidator.validateEmail(newEmailValue)) {
            throw new LogicException("invalid input parameter");
        }

        BaseDao baseDAO = BaseDaoImpl.getInstance();
        UserDao userDAO = UserDaoImpl.getInstance();
        try {
            if (baseDAO.matchUsername(usernameValue)) {
                userDAO.updateEmailByUsername(usernameValue, newEmailValue);
            } else {
                throw new LogicException("user doesn't exist");
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
