package by.makedon.finalproject.command.impl;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.logic.baselogic.AcceptRegistrationLogic;

import javax.servlet.http.HttpServletRequest;

public class AcceptRegistrationCommand implements Command {
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private AcceptRegistrationLogic logic;

    public AcceptRegistrationCommand(AcceptRegistrationLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String emailValue = req.getParameter(EMAIL);
        String usernameValue = req.getParameter(USERNAME);
        String password = req.getParameter(PASSWORD);

        String result = logic.doAction(emailValue, usernameValue, password);
        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + result);
        return router;
    }
}
