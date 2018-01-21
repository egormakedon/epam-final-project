package by.makedon.final_project.command.admincommand;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        router.setRoute(Router.RouteType.FORWARD);
        router.setPagePath(PageConstant.DELETE_USER_PAGE);
        return router;
    }
}
