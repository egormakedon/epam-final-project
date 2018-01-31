package by.makedon.selectioncommittee.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageConstant;
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
                router.setPagePath(PageConstant.FORWARD_PAGE + "?username=" + usernameValue + "&pagePath=" + PageConstant.CHANGE_EMAIL);
                break;
            case USERNAME:
                router.setPagePath(PageConstant.FORWARD_PAGE + "?username=" + usernameValue + "&pagePath=" + PageConstant.CHANGE_USERNAME);
                break;
            case PASSWORD:
                router.setPagePath(PageConstant.FORWARD_PAGE + "?username=" + usernameValue + "&pagePath=" + PageConstant.CHANGE_PASSWORD);
                break;
            default:
                router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=ChangeUserDataLinkCommand exception");
                break;
        }
        return router;
    }
}
