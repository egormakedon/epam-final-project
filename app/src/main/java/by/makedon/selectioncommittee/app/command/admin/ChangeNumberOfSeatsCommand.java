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
import java.util.Arrays;

public class ChangeNumberOfSeatsCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ChangeNumberOfSeatsCommand.class);

    private final Logic logic;

    public ChangeNumberOfSeatsCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String specialityValue = request.getParameter(RequestParameterKey.SPECIALITY);
        String numberOfSeatsValue = request.getParameter(RequestParameterKey.NUMBER_OF_SEATS);

        logger.debug("Execute ChangeNumberOfSeatsCommand: {}={}, {}={}",
            RequestParameterKey.SPECIALITY, specialityValue,
            RequestParameterKey.NUMBER_OF_SEATS, numberOfSeatsValue);

        RequestParameterBuilder parameterBuilder = RequestParameterBuilder.builder();
        try {
            logic.doAction(Arrays.asList(specialityValue, numberOfSeatsValue));
            parameterBuilder.put(RequestParameterKey.MESSAGE, "number of seats have been changed");
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            parameterBuilder.put(RequestParameterKey.MESSAGE, e.getMessage());
        }

        Router router = Router.redirectRouter();
        router.setPagePath(Page.MESSAGE + "?" + parameterBuilder.build());
        return router;
    }
}
