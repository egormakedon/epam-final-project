package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResetFormLogic implements Logic {
    private static final int LIST_SIZE = 1;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);

        UserDao dao = UserDaoImpl.getInstance();
        try {
            if (!dao.isStatementInProcess()) {
                throw new LogicException("you couldn't reset form");
            }
            if (!dao.isUserEnrolleeFormInserted(usernameValue)) {
                throw new LogicException("form has empty yet");
            }
            dao.resetEnrolleeFormByUsername(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
