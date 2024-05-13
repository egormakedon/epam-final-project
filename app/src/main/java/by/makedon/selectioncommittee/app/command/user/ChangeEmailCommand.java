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
import java.util.Arrays;

public class ChangeEmailCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ChangeEmailCommand.class);

    private final Logic logic;

    public ChangeEmailCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String usernameValue = request.getParameter(RequestParameterKey.USERNAME);
        String newEmailValue = request.getParameter(RequestParameterKey.NEW_EMAIL);

        logger.debug("Execute ChangeEmailCommand: {}={}, {}={}",
            RequestParameterKey.USERNAME, usernameValue,
            RequestParameterKey.NEW_EMAIL, newEmailValue);

        RequestParameterBuilder parameterBuilder = RequestParameterBuilder.builder();
        try {
            logic.doAction(Arrays.asList(usernameValue, newEmailValue));
            parameterBuilder.put(RequestParameterKey.MESSAGE, "email has been changed successfully!!");
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            parameterBuilder.put(RequestParameterKey.MESSAGE, e.getMessage());
        }

        Router router = Router.redirectRouter();
        router.setPagePath(Page.MESSAGE + "?" + parameterBuilder.build());
        return router;
    }
}
