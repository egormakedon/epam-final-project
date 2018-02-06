package by.makedon.selectioncommittee.logic.admin;

import by.makedon.selectioncommittee.dao.admin.AdminDAO;
import by.makedon.selectioncommittee.dao.admin.AdminDAOImpl;
import by.makedon.selectioncommittee.dao.base.BaseDAO;
import by.makedon.selectioncommittee.dao.base.BaseDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.validator.UserValidator;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class DeleteUserLogic implements Logic {
    private static final int LIST_SIZE = 1;

    private static final String ADMIN = "admin";

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);

        if (!UserValidator.validateUsername(usernameValue)) {
            throw new LogicException("invalid input parameters");
        }

        BaseDAO baseDAO = BaseDAOImpl.getInstance();
        AdminDAO adminDAO = AdminDAOImpl.getInstance();
        try {
            if (!baseDAO.matchUsername(usernameValue)) {
                throw new LogicException("user doesn't exist");
            }

            String type = baseDAO.takeTypeByUsername(usernameValue);
            if (type.equals(ADMIN)) {
                throw new LogicException("you can't delete this user");
            }

            adminDAO.deleteEnrolleeFormByUsername(usernameValue);
            adminDAO.deleteUserByUsername(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
