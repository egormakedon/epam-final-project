package by.makedon.selectioncommittee.command.admin;

import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ChangeNumberOfSeatsCommand implements Command {
    private static final String SPECIALITY = "speciality";
    private static final String NUMBER_OF_SEATS = "numberOfSeats";

    private Logic logic;

    public ChangeNumberOfSeatsCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String specialityValue = req.getParameter(SPECIALITY);
        String numberOfSeatsValue = req.getParameter(NUMBER_OF_SEATS);

        List<String> parameters = new ArrayList<String>();
        parameters.add(specialityValue);
        parameters.add(numberOfSeatsValue);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        try {
            logic.doAction(parameters);
            router.setPagePath(Page.MESSAGE + "?message=number of seats changed");
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(Page.MESSAGE + "?message=" + e.getMessage());
        }
        return router;
    }
}
