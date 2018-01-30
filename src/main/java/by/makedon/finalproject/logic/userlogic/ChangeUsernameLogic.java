package by.makedon.finalproject.logic.userlogic;

import by.makedon.finalproject.dao.userdao.UserDAO;
import by.makedon.finalproject.dao.userdao.UserDAOImpl;
import by.makedon.finalproject.exception.DAOException;
import by.makedon.finalproject.exception.LogicException;
import by.makedon.finalproject.validator.UserValidator;

public class ChangeUsernameLogic {
    public boolean doAction(String usernameValue, String newUsernameValue) throws LogicException {
        if (!UserValidator.validateUsername(newUsernameValue)) {
            throw new LogicException("wrong username");
        }

        UserDAO dao = UserDAOImpl.getInstance();
        try {
            return dao.changeUsername(usernameValue, newUsernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
