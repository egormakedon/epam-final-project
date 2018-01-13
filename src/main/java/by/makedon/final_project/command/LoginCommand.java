package by.makedon.final_project.command;

import by.makedon.final_project.constant.LoginState;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.logic.LoginLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private LoginLogic logic;

    LoginCommand(LoginLogic logic) {
        this.logic=logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        String usernameValue = req.getParameter(USERNAME);
        String passwordValue = req.getParameter(PASSWORD);

        if (!logic.validate(usernameValue, passwordValue)) {
            req.setAttribute("wrongdata", "input error");
            router.setRoute(Router.RouteType.FORWARD);
            router.setPagePath(PageConstant.MESSAGE_PAGE);
            return router;
        }

        try {
            if (!logic.match(usernameValue, passwordValue)) {
                req.setAttribute("wrongdata", "wrong username or password");
                router.setRoute(Router.RouteType.FORWARD);
                router.setPagePath(PageConstant.MESSAGE_PAGE);
                return router;
            } else {
                String type = logic.takeUserType(usernameValue);
                HttpSession session = req.getSession();
                session.setAttribute("username", usernameValue);
                session.setAttribute("type", type);
                session.setAttribute("login", LoginState.TRUE);
                router.setRoute(Router.RouteType.REDIRECT);
                router.setPagePath(PageConstant.USER);
                return router;
            }
        } catch (DAOException e) {
            errorPrint(req, router, LOGGER, e);
            return router;
        }
    }
}
