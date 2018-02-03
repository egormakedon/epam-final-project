package by.makedon.selectioncommittee.logic.base;

import by.makedon.selectioncommittee.dao.base.BaseDAO;
import by.makedon.selectioncommittee.dao.base.BaseDAOImpl;
import by.makedon.selectioncommittee.entity.user.User;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import com.sun.istack.internal.NotNull;

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

        BaseDAO dao = BaseDAOImpl.getInstance();
        try {
            User user = new User();
            user.setEmailValue(emailValue);
            user.setUsernameValue(usernameValue);
            user.setPasswordValue(passwordValue);
            dao.addUser(user);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}