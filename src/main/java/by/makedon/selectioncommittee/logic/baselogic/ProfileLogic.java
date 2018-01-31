package by.makedon.selectioncommittee.logic.baselogic;

//import by.makedon.selectioncommittee.constant.LoginState;
import by.makedon.selectioncommittee.constant.PageJSP;
import by.makedon.selectioncommittee.controller.Router;

public class ProfileLogic {
    private final static String USER = "user";

    public Router doAction(String loginValue, String typeValue) {
        Router router = new Router();
//        if (loginValue.equals(LoginState.FALSE)) {
//            router.setRoute(Router.RouteType.FORWARD);
//            router.setPagePath(PageJSP.LOGIN);
//            return router;
//        }
        router.setRoute(Router.RouteType.FORWARD);
        if (typeValue.equals(USER)) {
            router.setPagePath(PageJSP.USER);
        } else {
            router.setPagePath(PageJSP.ADMIN);
        }
        return router;
    }
}
