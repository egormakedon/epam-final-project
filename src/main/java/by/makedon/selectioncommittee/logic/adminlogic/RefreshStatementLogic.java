package by.makedon.selectioncommittee.logic.adminlogic;

import by.makedon.selectioncommittee.dao.admindao.AdminDAO;
import by.makedon.selectioncommittee.dao.admindao.AdminDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;

public class RefreshStatementLogic {
    public void doAction() throws DAOException {
        AdminDAO dao = AdminDAOImpl.getInstance();
        dao.resetStatement();
    }
}
