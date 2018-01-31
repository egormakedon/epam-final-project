package by.makedon.selectioncommittee.logic.userlogic;

import by.makedon.selectioncommittee.dao.userdao.UserDAO;
import by.makedon.selectioncommittee.dao.userdao.UserDAOImpl;
import by.makedon.selectioncommittee.entity.Enrollee;
import by.makedon.selectioncommittee.exception.DAOException;

public class ShowFormLogic {
    public Enrollee doAction(String usernameValue) throws DAOException {
        UserDAO dao = UserDAOImpl.getInstance();
        return dao.takeEnrollee(usernameValue);
    }
}
