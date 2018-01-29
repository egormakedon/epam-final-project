package by.makedon.finalproject.logic.baselogic;

import by.makedon.finalproject.constant.LoginState;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;

public class ProfileLogic {
    private final static String USER = "user";

    public Router doAction(String loginValue, String typeValue) {
        Router router = new Router();
        if (loginValue.equals(LoginState.FALSE)) {
            router.setRoute(Router.RouteType.FORWARD);
            router.setPagePath(PageConstant.LOGIN);
            return router;
        }
        router.setRoute(Router.RouteType.FORWARD);
        if (typeValue.equals(USER)) {
            router.setPagePath(PageConstant.USER);
        } else {
            router.setPagePath(PageConstant.ADMIN);
        }
        return router;
    }
}
