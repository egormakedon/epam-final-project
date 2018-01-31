package by.makedon.selectioncommittee.command.base;

import by.makedon.selectioncommittee.command.Command;
//import by.makedon.selectioncommittee.constant.LoginState;
import by.makedon.selectioncommittee.constant.PageJSP;
import by.makedon.selectioncommittee.controller.Router;

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
        //session.setAttribute(LOGIN, LoginState.FALSE);

        Router router = new Router();
        router.setPagePath(PageJSP.WELCOME);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}