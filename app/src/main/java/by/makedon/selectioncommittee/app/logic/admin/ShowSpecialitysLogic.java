package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.entity.SpecialityState;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class ShowSpecialitysLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 0;

    private final AdminDao adminDao = AdminDaoImpl.getInstance();
    private List<SpecialityState> specialityStateList = Collections.emptyList();

    @Override
    public int getValidParametersSize() {
        return VALID_PARAMETERS_SIZE;
    }

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        //empty
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        specialityStateList = Collections.emptyList();
        specialityStateList = adminDao.getSpecialityStateList();
    }

    public void updateServletRequest(HttpServletRequest request) {
        request.setAttribute(RequestParameterKey.SPECIALITY_LIST, specialityStateList);
    }
}
