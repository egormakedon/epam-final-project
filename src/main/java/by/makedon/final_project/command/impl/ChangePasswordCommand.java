package by.makedon.final_project.command.impl;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.logic.baselogic.ChangePasswordLogic;

import javax.servlet.http.HttpServletRequest;

public class ChangePasswordCommand implements Command {
    private static final String USERNAME = "username";
    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";
    private ChangePasswordLogic logic;

    public ChangePasswordCommand(ChangePasswordLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String usernameValue = req.getParameter(USERNAME);
        String password1Value = req.getParameter(PASSWORD1);
        String password2Value = req.getParameter(PASSWORD2);
        String message = logic.doAction(usernameValue, password1Value, password2Value);
        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + message);
        return router;
    }
}
