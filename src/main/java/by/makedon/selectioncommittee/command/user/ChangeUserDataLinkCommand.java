package by.makedon.selectioncommittee.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageJSP;
import by.makedon.selectioncommittee.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class ChangeUserDataLinkCommand implements Command {
    private static final String TYPE_CHANGER = "typechanger";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    public Router execute(HttpServletRequest req) {
        String dataTypeValue = req.getParameter(TYPE_CHANGER);
        String usernameValue = req.getParameter(USERNAME);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);

        switch (dataTypeValue) {
            case EMAIL:
                router.setPagePath(PageJSP.FORWARD_PAGE + "?username=" + usernameValue + "&pagePath=" + PageJSP.CHANGE_EMAIL);
                break;
            case USERNAME:
                router.setPagePath(PageJSP.FORWARD_PAGE + "?username=" + usernameValue + "&pagePath=" + PageJSP.CHANGE_USERNAME);
                break;
            case PASSWORD:
                router.setPagePath(PageJSP.FORWARD_PAGE + "?username=" + usernameValue + "&pagePath=" + PageJSP.CHANGE_PASSWORD);
                break;
            default:
                router.setPagePath(PageJSP.MESSAGE_PAGE + "?message=ChangeUserDataLinkCommand exception");
                break;
        }
        return router;
    }
}
