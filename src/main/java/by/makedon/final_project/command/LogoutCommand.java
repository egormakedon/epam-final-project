package by.makedon.final_project.command;

import by.makedon.final_project.constant.LoginState;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final String USERNAME = "username";
    private static final String USER_TYPE = "type";
    private static final String LOGIN = "login";

    @Override
    public Router execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute(USERNAME);
        session.removeAttribute(USER_TYPE);
        session.setAttribute(LOGIN, LoginState.FALSE);

        Router router = new Router();
        router.setPagePath(PageConstant.WELCOME);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}