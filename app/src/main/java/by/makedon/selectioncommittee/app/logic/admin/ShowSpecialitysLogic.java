package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.entity.SpecialityState;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import org.jetbrains.annotations.NotNull;

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

        AdminDao dao = AdminDaoImpl.getInstance();
        try {
            specialityStateList = dao.getSpecialityStateList();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public void updateServletRequest(HttpServletRequest req) {
        req.setAttribute(SPECIALITY_LIST, specialityStateList);
    }
}
