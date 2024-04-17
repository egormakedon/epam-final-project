package by.makedon.selectioncommittee.app.command.admin;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ForwardChangeNumberOfSeatsCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ForwardChangeNumberOfSeatsCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.debug("Execute ForwardChangeNumberOfSeatsCommand");

        Router router = Router.forwardRouter();
        router.setPagePath(Page.CHANGE_NUMBER_OF_SEATS);
        return router;
    }
}
