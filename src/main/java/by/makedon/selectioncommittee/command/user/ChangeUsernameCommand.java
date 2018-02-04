package by.makedon.selectioncommittee.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ChangeUsernameCommand implements Command {
    private static final String USERNAME = "username";
    private static final String NEW_USERNAME = "newusername";
    private static final String LOGIN = "login";
    private static final String FALSE = "false";

    private Logic logic;

    public ChangeUsernameCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String usernameValue = req.getParameter(USERNAME);
        String newUsernameValue = req.getParameter(NEW_USERNAME);

        List<String> parameters = new ArrayList<String>();
        parameters.add(usernameValue);
        parameters.add(newUsernameValue);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        try {
            logic.doAction(parameters);

            HttpSession session = req.getSession();
            if (session != null) {
                session.setAttribute(LOGIN, FALSE);
            }

            router.setPagePath(Page.MESSAGE + "?message=username changed successfully");
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(Page.MESSAGE + "?message=" + e.getMessage());
        }
        return router;
    }
}