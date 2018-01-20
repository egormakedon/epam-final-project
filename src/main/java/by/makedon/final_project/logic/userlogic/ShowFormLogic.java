package by.makedon.final_project.logic.userlogic;

import by.makedon.final_project.dao.userdao.UserDAO;
import by.makedon.final_project.dao.userdao.UserDAOImpl;
import by.makedon.final_project.entity.Enrollee;
import by.makedon.final_project.exception.DAOException;

public class ShowFormLogic {
    public Enrollee doAction(String usernameValue) throws DAOException {
        UserDAO dao = UserDAOImpl.getInstance();
        return dao.takeEnrollee(usernameValue);
    }
}
