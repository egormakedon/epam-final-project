package by.makedon.selectioncommittee.app.command.user;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ForwardSendFormCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ForwardSendFormCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.debug("Execute ForwardSendFormCommand");

        Router router = Router.forwardRouter();
        router.setPagePath(Page.SEND_FORM);
        return router;
    }
}
