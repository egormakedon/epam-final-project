package by.makedon.final_project.command;

import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.logic.ForgotpassLogic;

import javax.servlet.http.HttpServletRequest;

public class ForgotpassCommand implements Command {
    private static final String EMAIL = "email";
    private ForgotpassLogic logic;

    ForgotpassCommand(ForgotpassLogic logic) {
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
