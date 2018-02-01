package by.makedon.selectioncommittee.logic.base;

import by.makedon.selectioncommittee.dao.base.BaseDAO;
import by.makedon.selectioncommittee.dao.base.RegistrationDAO;
import by.makedon.selectioncommittee.entity.User;
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
        String password = parameters.get(2);

        if (!checkOnNull(emailValue, usernameValue, password)) {
            throw new LogicException("parameter is null");
        }

        BaseDAO dao = RegistrationDAO.getInstance();
        try {
            User user = new User();
            user.setEmailValue(emailValue);
            user.setUsernameValue(usernameValue);
            user.setPasswordValue(password);
            dao.addUser(user);
            return usernameValue + " register successfully";
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            return e.getMessage();
        }

    }

    private boolean checkOnNull(String arg0, String arg1, String arg2) {
        return arg0 != null && arg1 != null && arg2 != null;
    }
}