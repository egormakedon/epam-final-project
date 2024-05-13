package by.makedon.selectioncommittee.app.command.base;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterBuilder;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ForwardToChangePasswordCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ForwardToChangePasswordCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.debug("Execute ForwardToChangePasswordCommand");

        Router router = Router.redirectRouter();
        String requestParameters = RequestParameterBuilder
            .builder()
            .put(RequestParameterKey.PAGE_PATH, Page.CHANGE_PASSWORD)
            .build();
        router.setPagePath(Page.FORWARD + "?" + requestParameters);
        return router;
    }
}
