package by.makedon.selectioncommittee.app.command.base;

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

public class AcceptRegistrationCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(AcceptRegistrationCommand.class);

    private final Logic logic;

    public AcceptRegistrationCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String emailValue = request.getParameter(RequestParameterKey.EMAIL);
        String usernameValue = request.getParameter(RequestParameterKey.USERNAME);
        String password = request.getParameter(RequestParameterKey.PASSWORD);

        logger.debug("Execute AcceptRegistrationCommand: {}={}, {}={}, {}={}",
            RequestParameterKey.EMAIL, emailValue,
            RequestParameterKey.USERNAME, usernameValue,
            RequestParameterKey.PASSWORD, "*******");

        RequestParameterBuilder parameterBuilder = RequestParameterBuilder.builder();
        try {
            logic.doAction(Arrays.asList(emailValue, usernameValue, password));
            parameterBuilder.put(RequestParameterKey.MESSAGE,
                String.format("user '%s' has been registered successfully!!", usernameValue));
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            parameterBuilder.put(RequestParameterKey.MESSAGE, e.getMessage());
        }

        Router router = Router.redirectRouter();
        router.setPagePath(Page.MESSAGE + "?" + parameterBuilder.build());
        return router;
    }
}
