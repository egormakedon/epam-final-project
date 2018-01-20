package by.makedon.final_project.command.usercommand;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.entity.Enrollee;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.logic.userlogic.ShowFormLogic;
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
            router.setPagePath(PageConstant.MESSAGE_PAGE);
            return router;
        }
    }
}
