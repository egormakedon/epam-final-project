package by.makedon.selectioncommittee.logic.user;

import by.makedon.selectioncommittee.dao.user.UserDAO;
import by.makedon.selectioncommittee.dao.user.UserDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class ResetFormLogic implements Logic {
    private static final int LIST_SIZE = 1;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);

        UserDAO dao = UserDAOImpl.getInstance();
        try {
            if (!dao.couldChangeForm()) {
                throw new LogicException("you couldn't reset form");
            }
            if (!dao.isUserEnrolleeFormAdded(usernameValue)) {
                throw new LogicException("form has empty yet");
            }
            dao.resetForm(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
