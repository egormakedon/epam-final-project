package by.makedon.finalproject.command.impl;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.LoginState;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;

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