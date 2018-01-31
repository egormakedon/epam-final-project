package by.makedon.selectioncommittee.command.admin;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageJSP;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.logic.adminlogic.DeleteUserLogic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);
    private static final String USERNAME = "username";
    private DeleteUserLogic logic;

    public DeleteUserCommand(DeleteUserLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String usernameValue = (String)req.getAttribute(USERNAME);
        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        try {
            logic.doAction(usernameValue);
            router.setRoute(Router.RouteType.REDIRECT);
            router.setPagePath(PageJSP.MESSAGE_PAGE + "?message=" + usernameValue + " user has deleted from system");
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(PageJSP.MESSAGE_PAGE + "?message=" + e.getMessage());
        }
        return router;
    }
}
