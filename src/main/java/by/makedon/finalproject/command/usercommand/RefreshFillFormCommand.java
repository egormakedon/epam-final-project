package by.makedon.finalproject.command.usercommand;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.exception.DAOException;
import by.makedon.finalproject.logic.userlogic.RefreshFillFormLogic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RefreshFillFormCommand implements Command {
    private static final String USERNAME = "username";
    private static final Logger LOGGER = LogManager.getLogger(RefreshFillFormCommand.class);
    private RefreshFillFormLogic logic;

    public RefreshFillFormCommand(RefreshFillFormLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String usernameValue = (String) session.getAttribute(USERNAME);
        Router router;
        try {
            router = logic.doAction(usernameValue);
            return router;
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            router = new Router();
            router.setRoute(Router.RouteType.REDIRECT);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + e.getMessage());
            return router;
        }
    }
}
