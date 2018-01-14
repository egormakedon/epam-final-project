package by.makedon.final_project.command.impl;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;

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
