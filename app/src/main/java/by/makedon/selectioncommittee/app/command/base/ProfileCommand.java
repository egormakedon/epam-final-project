package by.makedon.selectioncommittee.app.command.base;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.base.ProfileLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class ProfileCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ProfileCommand.class);

    private final Logic logic;

    public ProfileCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String loginValue = (String) session.getAttribute(RequestParameterKey.LOGIN);
        String typeValue = (String) session.getAttribute(RequestParameterKey.TYPE);

        logger.debug("Execute ProfileCommand: {}={}, {}={}",
            RequestParameterKey.LOGIN, loginValue,
            RequestParameterKey.TYPE, typeValue);

        Router router = Router.forwardRouter();
        try {
            logic.doAction(Arrays.asList(loginValue, typeValue));

            ProfileLogic profileLogic = (ProfileLogic) logic;
            String pagePath = profileLogic.getPagePath();
            router.setPagePath(pagePath);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            request.setAttribute(RequestParameterKey.MESSAGE, e.getMessage());
            router.setPagePath(Page.MESSAGE);
        }

        return router;
    }
}
