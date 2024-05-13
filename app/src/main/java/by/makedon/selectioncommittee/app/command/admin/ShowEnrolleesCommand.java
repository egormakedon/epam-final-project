package by.makedon.selectioncommittee.app.command.admin;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.admin.ShowEnrolleesLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public class ShowEnrolleesCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ShowEnrolleesCommand.class);

    private final Logic logic;

    public ShowEnrolleesCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        logger.debug("Execute ShowEnrolleesCommand");

        Router router = Router.forwardRouter();
        try {
            logic.doAction(Collections.emptyList());
            ShowEnrolleesLogic showEnrolleesLogic = (ShowEnrolleesLogic) logic;
            showEnrolleesLogic.updateServletRequest(request);
            router.setPagePath(Page.SHOW_ENROLLEES);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            request.setAttribute(RequestParameterKey.MESSAGE, e.getMessage());
            router.setPagePath(Page.MESSAGE);
        }
        return router;
    }
}
