package by.makedon.finalproject.command.admincommand;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;

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
