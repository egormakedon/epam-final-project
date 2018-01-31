package by.makedon.selectioncommittee.logic.userlogic;

import by.makedon.selectioncommittee.dao.userdao.UserDAO;
import by.makedon.selectioncommittee.dao.userdao.UserDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.validator.UserValidator;

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
