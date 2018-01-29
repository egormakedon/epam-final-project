package by.makedon.finalproject.logic.adminlogic;

import by.makedon.finalproject.dao.admindao.AdminDAO;
import by.makedon.finalproject.dao.admindao.AdminDAOImpl;
import by.makedon.finalproject.exception.DAOException;

public class RefreshStatementLogic {
    public void doAction() throws DAOException {
        AdminDAO dao = AdminDAOImpl.getInstance();
        dao.resetStatement();
    }
}
