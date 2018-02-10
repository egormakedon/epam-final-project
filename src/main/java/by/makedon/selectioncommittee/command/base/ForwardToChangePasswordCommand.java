package by.makedon.selectioncommittee.command.base;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class ForwardToChangePasswordCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        router.setPagePath(Page.FORWARD + "?pagePath=" + Page.CHANGE_PASSWORD);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}