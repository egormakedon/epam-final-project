package by.makedon.selectioncommittee.logic.user;

import by.makedon.selectioncommittee.dao.user.UserDAO;
import by.makedon.selectioncommittee.dao.user.UserDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import com.sun.istack.internal.NotNull;

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

        UserDAO dao = UserDAOImpl.getInstance();
        try {
            statement = dao.takeStatementByUsername(usernameValue);
            long specialityIdValue = dao.takeSpecialityIdByUsername(usernameValue);
            place = dao.takeEnrolleePlaceByUsernameSpecialityID(usernameValue, specialityIdValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public void updateServletRequest(HttpServletRequest req) {
        req.setAttribute(STATEMENT, statement);
        req.setAttribute(PLACE, place);
    }
}
