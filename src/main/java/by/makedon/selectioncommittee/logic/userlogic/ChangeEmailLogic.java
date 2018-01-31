package by.makedon.selectioncommittee.logic.userlogic;

import by.makedon.selectioncommittee.dao.userdao.UserDAO;
import by.makedon.selectioncommittee.dao.userdao.UserDAOImpl;
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
