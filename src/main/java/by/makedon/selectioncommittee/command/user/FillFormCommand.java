package by.makedon.selectioncommittee.command.user;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageConstant;
import by.makedon.selectioncommittee.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class FillFormCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        router.setRoute(Router.RouteType.FORWARD);
        router.setPagePath(PageConstant.FILL_FORM_PAGE);
        return router;
    }
}
