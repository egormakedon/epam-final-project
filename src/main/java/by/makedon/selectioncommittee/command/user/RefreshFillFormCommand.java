package by.makedon.selectioncommittee.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageConstant;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.logic.userlogic.RefreshFillFormLogic;
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
