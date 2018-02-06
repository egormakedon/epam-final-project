package by.makedon.selectioncommittee.command.admin;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DeleteUserCommand implements Command {
    private static final String USERNAME = "username";

    private Logic logic;

    public DeleteUserCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String usernameValue = req.getParameter(USERNAME);

        List<String> parameters = new ArrayList<String>();
        parameters.add(usernameValue);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        try {
            logic.doAction(parameters);
            router.setPagePath(Page.MESSAGE + "?message=" + usernameValue + " user has deleted");
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(Page.MESSAGE + "?message=" + e.getMessage());
        }
        return router;
    }
}