package by.makedon.selectioncommittee.app.command.admin;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ForwardDeleteUserCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ForwardDeleteUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.debug("Execute ForwardDeleteUserCommand");

        Router router = Router.forwardRouter();
        router.setPagePath(Page.DELETE_USER);
        return router;
    }
}
