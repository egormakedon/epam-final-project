package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CheckStatusLogic implements Logic {
    private static final int LIST_SIZE = 1;

    private static final String STATEMENT = "statement";
    private static final String PLACE = "place";

    private String statement;
    private int place;

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);

        UserDao dao = UserDaoImpl.getInstance();
        try {
            statement = dao.getStatementByUsername(usernameValue);
            long specialityIdValue = dao.getSpecialityIdByUsername(usernameValue);
            place = dao.getEnrolleePlaceByUsernameAndSpecialityId(usernameValue, specialityIdValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public void updateServletRequest(HttpServletRequest req) {
        req.setAttribute(STATEMENT, statement);
        req.setAttribute(PLACE, place);
    }
}
