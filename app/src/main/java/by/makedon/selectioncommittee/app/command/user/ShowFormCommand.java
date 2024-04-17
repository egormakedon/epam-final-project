package by.makedon.selectioncommittee.app.command.user;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.user.ShowFormLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;

public class ShowFormCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ShowFormCommand.class);

    private final Logic logic;

    public ShowFormCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String usernameValue = (String) session.getAttribute(RequestParameterKey.USERNAME);

        logger.debug("Execute ShowFormCommand: {}={}",
            RequestParameterKey.USERNAME, usernameValue);

        Router router = Router.forwardRouter();
        try {
            logic.doAction(Collections.singletonList(usernameValue));
            ShowFormLogic showFormLogic = (ShowFormLogic) logic;
            showFormLogic.updateServletRequest(request);
            router.setPagePath(Page.SHOW_FORM);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            request.setAttribute(RequestParameterKey.MESSAGE, e.getMessage());
            router.setPagePath(Page.MESSAGE);
        }
        return router;
    }
}
