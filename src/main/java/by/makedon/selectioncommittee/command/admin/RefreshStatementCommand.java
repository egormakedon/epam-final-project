package by.makedon.selectioncommittee.command.admin;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageJSP;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.logic.adminlogic.RefreshStatementLogic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RefreshStatementCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RefreshStatementCommand.class);
    private RefreshStatementLogic logic;

    public RefreshStatementCommand(RefreshStatementLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);

        try {
            logic.doAction();
            router.setPagePath(PageJSP.MESSAGE_PAGE + "?message=all statement have refreshed successfully");
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(PageJSP.MESSAGE_PAGE + "?message=" + e.getMessage());
        }

        return router;
    }
}
