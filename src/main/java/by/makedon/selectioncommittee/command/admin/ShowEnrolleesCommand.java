package by.makedon.selectioncommittee.command.admin;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.logic.admin.ShowEnrolleesLogic;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowEnrolleesCommand implements Command {
    private static final String MESSAGE = "message";

    private Logic logic;

    public ShowEnrolleesCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        List<String> parameters = new ArrayList<String>();

        Router router = new Router();
        router.setRoute(Router.RouteType.FORWARD);
        try {
            logic.doAction(parameters);
            ShowEnrolleesLogic showEnrolleesLogic = (ShowEnrolleesLogic) logic;
            showEnrolleesLogic.updateServletRequest(req);
            router.setPagePath(Page.SHOW_ENROLLEES);
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            req.setAttribute(MESSAGE, e.getMessage());
            router.setPagePath(Page.MESSAGE);
        }
        return router;
    }
}