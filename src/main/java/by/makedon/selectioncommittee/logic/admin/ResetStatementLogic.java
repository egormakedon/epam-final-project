package by.makedon.selectioncommittee.logic.admin;

import by.makedon.selectioncommittee.dao.admin.AdminDAO;
import by.makedon.selectioncommittee.dao.admin.AdminDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class ResetStatementLogic implements Logic {

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (!parameters.isEmpty()) {
            throw new LogicException("wrong number of parameters");
        }

        AdminDAO dao = AdminDAOImpl.getInstance();
        try {
            dao.resetStatement();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
