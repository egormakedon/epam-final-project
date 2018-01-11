package by.makedon.final_project.command;

import by.makedon.final_project.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class EpamLinkCommand implements Command {
    private static final String EPAM_PAGE_URL = "http://epam.by";

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        router.setPagePath(EPAM_PAGE_URL);
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
