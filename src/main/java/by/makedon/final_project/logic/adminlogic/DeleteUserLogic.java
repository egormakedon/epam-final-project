package by.makedon.final_project.logic.adminlogic;

import by.makedon.final_project.dao.admindao.AdminDAO;
import by.makedon.final_project.dao.admindao.AdminDAOImpl;
import by.makedon.final_project.exception.DAOException;

public class DeleteUserLogic {
    public void doAction(String usernameValue) throws DAOException {
        AdminDAO dao = AdminDAOImpl.getInstance();
        dao.deleteUser(usernameValue);
    }
}
