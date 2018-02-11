package by.makedon.selectioncommittee.logic.admin;

import by.makedon.selectioncommittee.dao.admin.AdminDAO;
import by.makedon.selectioncommittee.dao.admin.AdminDAOImpl;
import by.makedon.selectioncommittee.entity.speciality.SpecialityState;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import com.sun.istack.internal.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowSpecialitysLogic implements Logic {
    private static final String SPECIALITY_LIST = "specialityList";
    private List<SpecialityState> specialityStateList;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (!parameters.isEmpty()) {
            throw new LogicException("wrong number of parameters");
        }

        AdminDAO dao = AdminDAOImpl.getInstance();
        try {
            specialityStateList = dao.takeSpecialityStateList();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public void updateServletRequest(HttpServletRequest req) {
        req.setAttribute(SPECIALITY_LIST, specialityStateList);
    }
}
