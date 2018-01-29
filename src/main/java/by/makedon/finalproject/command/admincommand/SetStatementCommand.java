package by.makedon.finalproject.command.admincommand;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.exception.DAOException;
import by.makedon.finalproject.logic.adminlogic.SetStatementLogic;

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