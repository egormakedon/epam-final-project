package by.makedon.selectioncommittee.logic.admin;

import by.makedon.selectioncommittee.dao.admin.AdminDAO;
import by.makedon.selectioncommittee.dao.admin.AdminDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;

public class RefreshStatementLogic {
    public void doAction() throws DAOException {
        AdminDAO dao = AdminDAOImpl.getInstance();
        dao.resetStatement();
    }
}
