package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResetStatementLogic implements Logic {
    private final AdminDao adminDao = AdminDaoImpl.getInstance();

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        if (!parameters.isEmpty()) {
            final String message = String.format(
                "Invalid input parameters size: expected=`0`, actual=`%d`", parameters.size());
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        adminDao.resetStatement();
    }
}
