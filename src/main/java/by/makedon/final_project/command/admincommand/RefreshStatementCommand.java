package by.makedon.final_project.command.admincommand;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.logic.adminlogic.RefreshStatementLogic;
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
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=all statement have refreshed successfully");
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + e.getMessage());
        }

        return router;
    }
}
