package by.makedon.final_project.command;

import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class EpamLinkCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        router.setPagePath(PageConstant.EPAM);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
