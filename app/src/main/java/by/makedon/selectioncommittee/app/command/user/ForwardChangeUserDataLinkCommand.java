package by.makedon.selectioncommittee.app.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class ForwardChangeUserDataLinkCommand implements Command {
    private static final String TYPE_CHANGER = "typechanger";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    public Router execute(HttpServletRequest req) {
        String usernameValue = req.getParameter(USERNAME);
        String typeChangerValue = req.getParameter(TYPE_CHANGER);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        switch (typeChangerValue) {
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
                router.setPagePath(Page.MESSAGE + "?message=invalid parameter");
                break;
        }
        return router;
    }
}
