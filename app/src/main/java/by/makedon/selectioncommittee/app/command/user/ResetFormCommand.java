package by.makedon.selectioncommittee.app.command.user;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterBuilder;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;

public class ResetFormCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ResetFormCommand.class);

    private final Logic logic;

    public ResetFormCommand(Logic logic) { this.logic = logic; }

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String usernameValue = (String) session.getAttribute(RequestParameterKey.USERNAME);

        logger.debug("Execute ResetFormCommand: {}={}",
            RequestParameterKey.USERNAME, usernameValue);

        RequestParameterBuilder parameterBuilder = RequestParameterBuilder.builder();
        try {
            logic.doAction(Collections.singletonList(usernameValue));
            parameterBuilder.put(RequestParameterKey.MESSAGE, "form has been reset successfully!!");
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            parameterBuilder.put(RequestParameterKey.MESSAGE, e.getMessage());
        }

        Router router = Router.redirectRouter();
        router.setPagePath(Page.MESSAGE + "?" + parameterBuilder.build());
        return router;
    }
}
