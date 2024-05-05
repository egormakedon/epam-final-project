package by.makedon.selectioncommittee.app.logic.base;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.entity.User;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AcceptRegistrationLogic implements Logic {
    private static final int LIST_SIZE = 3;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String emailValue = parameters.get(0);
        String usernameValue = parameters.get(1);
        String passwordValue = parameters.get(2);

        BaseDao dao = BaseDaoImpl.getInstance();
        try {
            if (matchEmailUsername(emailValue, usernameValue)) {
                throw new DaoException("this user already exist");
            }

            User user = new User();
            user.setEmail(emailValue);
            user.setUsername(usernameValue);
            user.setPassword(passwordValue);
            dao.insertUser(user);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
