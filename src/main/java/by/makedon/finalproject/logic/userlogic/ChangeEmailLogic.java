package by.makedon.finalproject.logic.userlogic;

import by.makedon.finalproject.dao.userdao.UserDAO;
import by.makedon.finalproject.dao.userdao.UserDAOImpl;
import by.makedon.finalproject.exception.DAOException;
import by.makedon.finalproject.exception.LogicException;
import by.makedon.finalproject.validator.UserValidator;

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
