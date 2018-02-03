package by.makedon.selectioncommittee.command.base;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.logic.base.LoginLogic;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class LoginCommand implements Command {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String TYPE = "type";
    private static final String LOGIN = "login";
    private static final String TRUE = "true";

    private Logic logic;

    public LoginCommand(Logic logic) {
        this.logic=logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String usernameValue = req.getParameter(USERNAME);
        String passwordValue = req.getParameter(PASSWORD);

        List<String> parameters = new ArrayList<String>();
        parameters.add(usernameValue);
        parameters.add(passwordValue);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        try {
            logic.doAction(parameters);

            LoginLogic loginLogic = (LoginLogic) logic;
            String type = loginLogic.getType();

            HttpSession session = req.getSession();
            session.setAttribute(USERNAME, usernameValue);
            session.setAttribute(TYPE, type);
            session.setAttribute(LOGIN, TRUE);

            router.setPagePath(Page.FORWARD + "?pagePath=" + Page.USER);
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(Page.MESSAGE + "?message=" + e.getMessage());
        }
        return router;
    }
}
