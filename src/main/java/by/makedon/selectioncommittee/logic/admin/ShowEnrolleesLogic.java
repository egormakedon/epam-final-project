package by.makedon.selectioncommittee.logic.admin;

import by.makedon.selectioncommittee.dao.admin.AdminDAO;
import by.makedon.selectioncommittee.dao.admin.AdminDAOImpl;
import by.makedon.selectioncommittee.entity.enrollee.AdminEnrolleeForm;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import com.sun.istack.internal.NotNull;

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

        AdminDAO dao = AdminDAOImpl.getInstance();
        try {
            adminEnrolleeFormList = dao.takeAdminEnrolleeFormList();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public void updateServletRequest(HttpServletRequest req) {
        req.setAttribute(ENROLLEE_LIST, adminEnrolleeFormList);
    }
}
