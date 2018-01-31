package by.makedon.selectioncommittee.command.base;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageConstant;
import by.makedon.selectioncommittee.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class ForwardChangePasswordCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        router.setPagePath(PageConstant.CHANGE_PASSWORD);
        router.setRoute(Router.RouteType.FORWARD);
        return router;
    }
}
