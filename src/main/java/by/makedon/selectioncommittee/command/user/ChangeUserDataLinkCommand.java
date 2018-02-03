package by.makedon.selectioncommittee.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
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
                router.setPagePath(Page.FORWARD + "?username=" + usernameValue + "&pagePath=" + Page.CHANGE_EMAIL);
                break;
            case USERNAME:
                router.setPagePath(Page.FORWARD + "?username=" + usernameValue + "&pagePath=" + Page.CHANGE_USERNAME);
                break;
            case PASSWORD:
                router.setPagePath(Page.FORWARD + "?username=" + usernameValue + "&pagePath=" + Page.CHANGE_PASSWORD);
                break;
            default:
                router.setPagePath(Page.MESSAGE + "?message=ChangeUserDataLinkCommand exception");
                break;
        }
        return router;
    }
}
