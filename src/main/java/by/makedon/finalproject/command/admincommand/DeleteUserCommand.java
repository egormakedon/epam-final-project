package by.makedon.finalproject.command.admincommand;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.exception.DAOException;
import by.makedon.finalproject.logic.adminlogic.DeleteUserLogic;
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
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + usernameValue + " user has deleted from system");
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + e.getMessage());
        }
        return router;
    }
}
