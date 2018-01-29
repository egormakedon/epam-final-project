package by.makedon.finalproject.command.impl;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.logic.baselogic.ForgotPasswordLogic;

import javax.servlet.http.HttpServletRequest;

public class ForgotPasswordCommand implements Command {
    private static final String EMAIL = "email";
    private ForgotPasswordLogic logic;

    public ForgotPasswordCommand(ForgotPasswordLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String emailValue = req.getParameter(EMAIL);
        logic.doAction(emailValue);
        Router router = new Router();
        router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=Sending in progress...");
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
