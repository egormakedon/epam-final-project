package by.makedon.finalproject.command.usercommand;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class ChangeUserDataLinkCommand implements Command {
    private static final String TYPE_CHANGER = "typechanger";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    public Router execute(HttpServletRequest req) {
        String dataTypeValue = (String)req.getAttribute(TYPE_CHANGER);
        String usernameValue = (String)req.getAttribute(USERNAME);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);

        switch (dataTypeValue) {
            case EMAIL:
                router.setPagePath(PageConstant.CHANGE_EMAIL + "?username=" + usernameValue);
                break;
            case USERNAME:
                router.setPagePath(PageConstant.CHANGE_USERNAME + "?username=" + usernameValue);
                break;
            case PASSWORD:
                router.setPagePath(PageConstant.CHANGE_PASSWORD + "?username=" + usernameValue);
                break;
            default:
                router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=ChangeUserDataLinkCommand exception");
                break;
        }
        return router;
    }
}
