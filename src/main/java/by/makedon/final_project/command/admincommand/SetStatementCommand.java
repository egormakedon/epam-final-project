package by.makedon.final_project.command.admincommand;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.logic.adminlogic.SetStatementLogic;

import javax.servlet.http.HttpServletRequest;

public class SetStatementCommand implements Command {
    private SetStatementLogic logic;

    public SetStatementCommand(SetStatementLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        try {
            logic.doAction();
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=statement has set successfully");
        } catch (DAOException e) {
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + e.getMessage());
        }
        return router;
    }
}