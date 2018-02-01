package by.makedon.selectioncommittee.logic.admin;

import by.makedon.selectioncommittee.dao.admin.AdminDAO;
import by.makedon.selectioncommittee.dao.admin.AdminDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;

public class DeleteUserLogic {
    public void doAction(String usernameValue) throws DAOException {
        AdminDAO dao = AdminDAOImpl.getInstance();
        dao.deleteUser(usernameValue);
    }
}
