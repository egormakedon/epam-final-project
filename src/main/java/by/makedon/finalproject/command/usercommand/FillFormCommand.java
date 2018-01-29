package by.makedon.finalproject.command.usercommand;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;

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
