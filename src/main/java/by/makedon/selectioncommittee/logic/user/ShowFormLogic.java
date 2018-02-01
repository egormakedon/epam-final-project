package by.makedon.selectioncommittee.logic.user;

import by.makedon.selectioncommittee.dao.user.UserDAO;
import by.makedon.selectioncommittee.dao.user.UserDAOImpl;
import by.makedon.selectioncommittee.entity.Enrollee;
import by.makedon.selectioncommittee.exception.DAOException;

public class ShowFormLogic {
    public Enrollee doAction(String usernameValue) throws DAOException {
        UserDAO dao = UserDAOImpl.getInstance();
        return dao.takeEnrollee(usernameValue);
    }
}
