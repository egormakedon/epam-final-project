package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResetStatementLogic implements Logic {

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (!parameters.isEmpty()) {
            throw new LogicException("wrong number of parameters");
        }

        AdminDao dao = AdminDaoImpl.getInstance();
        try {
            dao.resetStatement();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
