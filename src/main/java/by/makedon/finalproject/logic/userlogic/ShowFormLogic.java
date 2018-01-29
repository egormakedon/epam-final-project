package by.makedon.finalproject.logic.userlogic;

import by.makedon.finalproject.dao.userdao.UserDAO;
import by.makedon.finalproject.dao.userdao.UserDAOImpl;
import by.makedon.finalproject.entity.Enrollee;
import by.makedon.finalproject.exception.DAOException;

public class ShowFormLogic {
    public Enrollee doAction(String usernameValue) throws DAOException {
        UserDAO dao = UserDAOImpl.getInstance();
        return dao.takeEnrollee(usernameValue);
    }
}
