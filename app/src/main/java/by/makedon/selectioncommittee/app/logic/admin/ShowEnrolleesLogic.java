package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.entity.AdminEnrolleeForm;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowEnrolleesLogic implements Logic {
    private static final String ENROLLEE_LIST = "enrolleeList";
    private List<AdminEnrolleeForm> adminEnrolleeFormList;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (!parameters.isEmpty()) {
            throw new LogicException("wrong number of parameters");
        }

        AdminDao dao = AdminDaoImpl.getInstance();
        try {
            adminEnrolleeFormList = dao.getAdminEnrolleeFormList();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public void updateServletRequest(HttpServletRequest req) {
        req.setAttribute(ENROLLEE_LIST, adminEnrolleeFormList);
    }
}
