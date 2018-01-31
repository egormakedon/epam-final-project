package by.makedon.selectioncommittee.command.admin;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.PageJSP;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.logic.adminlogic.SetStatementLogic;

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
            router.setPagePath(PageJSP.MESSAGE_PAGE + "?message=statement has set successfully");
        } catch (DAOException e) {
            router.setPagePath(PageJSP.MESSAGE_PAGE + "?message=" + e.getMessage());
        }
        return router;
    }
}