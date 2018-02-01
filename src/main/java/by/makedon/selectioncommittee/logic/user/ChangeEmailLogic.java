package by.makedon.selectioncommittee.logic.user;

import by.makedon.selectioncommittee.dao.user.UserDAO;
import by.makedon.selectioncommittee.dao.user.UserDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.validator.UserValidator;

public class ChangeEmailLogic {
    public void doAction(String usernameValue, String newEmailValue) throws LogicException {
        if (!UserValidator.validateEmail(newEmailValue)) {
            throw new LogicException("wrong email");
        }

        UserDAO dao = UserDAOImpl.getInstance();
        try {
            dao.takeEmail(usernameValue);
            dao.changeEmail(usernameValue, newEmailValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
