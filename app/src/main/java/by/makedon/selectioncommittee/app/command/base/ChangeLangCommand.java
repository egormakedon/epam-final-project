package by.makedon.selectioncommittee.app.command.base;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLangCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ChangeLangCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        String langValue = request.getParameter(RequestParameterKey.LANG);

        logger.debug("Execute ChangeLangCommand: {}={}",
            RequestParameterKey.LANG, langValue);

        HttpSession session = request.getSession();
        session.setAttribute(RequestParameterKey.LANG, langValue);

        Router router = Router.redirectRouter();
        String lastPageValue = (String) session.getAttribute(RequestParameterKey.LAST_PAGE);
        router.setPagePath(lastPageValue);
        return router;
    }
}
