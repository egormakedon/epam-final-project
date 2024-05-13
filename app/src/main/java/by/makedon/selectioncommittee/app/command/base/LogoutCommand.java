package by.makedon.selectioncommittee.app.command.base;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LogoutCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.debug("Execute LogoutCommand");

        HttpSession session = request.getSession();
        session.removeAttribute(RequestParameterKey.USERNAME);
        session.removeAttribute(RequestParameterKey.TYPE);
        session.setAttribute(RequestParameterKey.LOGIN, Boolean.FALSE.toString());

        Router router = Router.redirectRouter();
        router.setPagePath(Page.WELCOME);
        return router;
    }
}
