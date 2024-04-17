package by.makedon.selectioncommittee.app.command.base;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.controller.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLangCommand implements Command {
    private static final String LANG = "lang";
    private static final String LAST_PAGE = "lastPage";

    @Override
    public Router execute(HttpServletRequest req) {
        String langValue = req.getParameter(LANG);

        HttpSession session = req.getSession();
        session.setAttribute(LANG, langValue);
        String lastPageValue = (String)session.getAttribute(LAST_PAGE);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        router.setPagePath(lastPageValue);
        return router;
    }
}
