package by.makedon.selectioncommittee.app.command.admin;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.admin.ShowSpecialitysLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public class ShowSpecialitysCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ShowSpecialitysCommand.class);

    private final Logic logic;

    public ShowSpecialitysCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        logger.debug("Execute ShowSpecialitysCommand");

        Router router = Router.forwardRouter();
        try {
            logic.doAction(Collections.emptyList());
            ShowSpecialitysLogic showSpecialitysLogic = (ShowSpecialitysLogic) logic;
            showSpecialitysLogic.updateServletRequest(request);
            router.setPagePath(Page.SHOW_SPECIALITYS);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            request.setAttribute(RequestParameterKey.MESSAGE, e.getMessage());
            router.setPagePath(Page.MESSAGE);
        }
        return router;
    }
}
