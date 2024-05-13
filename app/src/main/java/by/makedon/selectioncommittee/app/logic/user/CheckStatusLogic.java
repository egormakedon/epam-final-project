package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CheckStatusLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 1;

    private final UserDao userDao = UserDaoImpl.getInstance();

    private String statement = "";
    private int place;

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
        statement = "";
        place = 0;

        String username = parameters.get(0);
        long specialityId = userDao.getSpecialityIdByUsername(username);

        statement = userDao.getStatementByUsername(username);
        place = userDao.getEnrolleePlaceByUsernameAndSpecialityId(username, specialityId);
    }

    public void updateServletRequest(HttpServletRequest request) {
        request.setAttribute(RequestParameterKey.STATEMENT, statement);
        request.setAttribute(RequestParameterKey.PLACE, place);
    }
}
