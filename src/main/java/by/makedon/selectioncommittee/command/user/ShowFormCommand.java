package by.makedon.selectioncommittee.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageJSP;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.entity.Enrollee;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.logic.userlogic.ShowFormLogic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShowFormCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowFormCommand.class);
    private static final String USERNAME = "username";
    private ShowFormLogic logic;

    public ShowFormCommand(ShowFormLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String usernameValue = (String)session.getAttribute(USERNAME);

        Router router = new Router();
        try {
            Enrollee enrollee = logic.doAction(usernameValue);
            ///
            return null;
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            req.setAttribute("message", e.getMessage());
            router.setRoute(Router.RouteType.FORWARD);
            router.setPagePath(PageJSP.MESSAGE_PAGE);
            return router;
        }
    }
}
