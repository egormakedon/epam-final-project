package by.makedon.selectioncommittee.app.command.user;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ForwardChangeUserDataCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ForwardChangeUserDataCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.debug("Execute ForwardChangeUserDataCommand");

        Router router = Router.forwardRouter();
        router.setPagePath(Page.CHANGE_USER_DATA);
        return router;
    }
}
