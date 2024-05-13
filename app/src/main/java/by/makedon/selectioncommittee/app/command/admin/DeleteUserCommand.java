package by.makedon.selectioncommittee.app.command.admin;

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
import java.util.Collections;

public class DeleteUserCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteUserCommand.class);

    private final Logic logic;

    public DeleteUserCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String usernameValue = request.getParameter(RequestParameterKey.USERNAME);

        logger.debug("Execute DeleteUserCommand: {}={}",
            RequestParameterKey.USERNAME, usernameValue);

        RequestParameterBuilder parameterBuilder = RequestParameterBuilder.builder();
        try {
            logic.doAction(Collections.singletonList(usernameValue));
            parameterBuilder.put(RequestParameterKey.MESSAGE, usernameValue + " user has been deleted");
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            parameterBuilder.put(RequestParameterKey.MESSAGE, e.getMessage());
        }

        Router router = Router.redirectRouter();
        router.setPagePath(Page.MESSAGE + "?" + parameterBuilder.build());
        return router;
    }
}
