package by.makedon.finalproject.command.impl;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;

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
