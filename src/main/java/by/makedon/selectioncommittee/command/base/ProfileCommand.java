package by.makedon.selectioncommittee.command.base;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageJSP;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.logic.base.ProfileLogic;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ProfileCommand implements Command {
    private static final String LOGIN = "login";
    private static final String TYPE = "type";
    private static final String MESSAGE = "message";

    private Logic logic;

    public ProfileCommand(Logic logic) {
        this.logic=logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String loginValue = (String)req.getSession().getAttribute(LOGIN);
        String typeValue = (String)req.getSession().getAttribute(TYPE);

        List<String> parameters = new ArrayList<String>();
        parameters.add(loginValue);
        parameters.add(typeValue);

        Router router = new Router();
        router.setRoute(Router.RouteType.FORWARD);
        try {
            logic.doAction(parameters);

            ProfileLogic profileLogic = (ProfileLogic) logic;
            String pagePath = profileLogic.getPagePath();

            router.setPagePath(pagePath);
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            req.setAttribute(MESSAGE, e.getMessage());
            router.setPagePath(PageJSP.MESSAGE_PAGE);
        }
        return router;
    }
}
