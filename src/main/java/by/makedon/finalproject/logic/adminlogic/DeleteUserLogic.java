package by.makedon.finalproject.logic.adminlogic;

import by.makedon.finalproject.dao.admindao.AdminDAO;
import by.makedon.finalproject.dao.admindao.AdminDAOImpl;
import by.makedon.finalproject.exception.DAOException;

public class DeleteUserLogic {
    public void doAction(String usernameValue) throws DAOException {
        AdminDAO dao = AdminDAOImpl.getInstance();
        dao.deleteUser(usernameValue);
    }
}
