package by.makedon.finalproject.command.usercommand;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.exception.LogicException;
import by.makedon.finalproject.logic.userlogic.ChangeEmailLogic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeEmailCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangeEmailCommand.class);
    private static final String NEW_EMAIL = "newemail";
    private static final String USERNAME = "username";
    private ChangeEmailLogic logic;

    public ChangeEmailCommand(ChangeEmailLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String newEmailValue = req.getParameter(NEW_EMAIL);
        String usernameValue = req.getParameter(USERNAME);
        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        try {
            logic.doAction(usernameValue, newEmailValue);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=email changed successfully");
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + e);
        }
        return router;
    }
}