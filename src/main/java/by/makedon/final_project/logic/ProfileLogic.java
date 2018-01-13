package by.makedon.final_project.logic;

import by.makedon.final_project.constant.LoginState;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;

public class ProfileLogic {
    private final static String USER = "user";

    public Router doAction(String loginValue, String typeValue) {
        Router router = new Router();
        if (loginValue == null || loginValue.equals(LoginState.FALSE)) {
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
