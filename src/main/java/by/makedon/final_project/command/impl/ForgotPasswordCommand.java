package by.makedon.final_project.command.impl;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.logic.baselogic.ForgotPasswordLogic;

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
