package by.makedon.final_project.logic.userlogic;

import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;

public class RefreshFillFormLogic {
    public Router doAction(String usernameValue) {




        Router router = new Router();
        router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + "form has refreshed successfully");
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
