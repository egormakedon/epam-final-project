package by.makedon.selectioncommittee.command.base;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageJSP;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.logic.baselogic.RegistrationLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";
    private RegistrationLogic logic;

    public RegistrationCommand(RegistrationLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        String emailValue = req.getParameter(EMAIL);
        String usernameValue = req.getParameter(USERNAME);
        String password1Value = req.getParameter(PASSWORD1);
        String password2Value = req.getParameter(PASSWORD2);

        String result = logic.doAction(emailValue, usernameValue, password1Value, password2Value);
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(PageJSP.MESSAGE_PAGE + "?message=" + result);
        return router;
    }
}