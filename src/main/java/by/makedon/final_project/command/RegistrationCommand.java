package by.makedon.final_project.command;

import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.logic.RegistrationLogic;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";
    private RegistrationLogic logic;

    RegistrationCommand(RegistrationLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        String emailValue = req.getParameter(EMAIL);
        String usernameValue = req.getParameter(USERNAME);
        String password1Value = req.getParameter(PASSWORD1);
        String password2Value = req.getParameter(PASSWORD2);

        if (!logic.validate(emailValue, usernameValue, password1Value, password2Value)) {
            req.setAttribute("wrongdata", "input error");
            router.setRoute(Router.RouteType.FORWARD);
            router.setPagePath(PageConstant.MESSAGE_PAGE);
            return router;
        }



        return null;
    }
}
