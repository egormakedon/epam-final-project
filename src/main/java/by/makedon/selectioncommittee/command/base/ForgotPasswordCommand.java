package by.makedon.selectioncommittee.command.base;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageConstant;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.logic.baselogic.ForgotPasswordLogic;

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
