package by.makedon.selectioncommittee.app.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.logic.user.CheckStatusLogic;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class CheckStatusCommand implements Command {
    private static final String USERNAME = "username";
    private static final String MESSAGE = "message";

    private Logic logic;

    public CheckStatusCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String usernameValue = (String)session.getAttribute(USERNAME);

        List<String> parameters = new ArrayList<String>();
        parameters.add(usernameValue);

        Router router = new Router();
        router.setRoute(Router.RouteType.FORWARD);
        try {
            logic.doAction(parameters);
            CheckStatusLogic checkStatusLogic = (CheckStatusLogic) logic;
            checkStatusLogic.updateServletRequest(req);
            router.setPagePath(Page.CHECK_STATUS);
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            req.setAttribute(MESSAGE, e.getMessage());
            router.setPagePath(Page.MESSAGE);
        }
        return router;
    }
}
