package by.makedon.finalproject.command.admincommand;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.exception.LogicException;
import by.makedon.finalproject.logic.adminlogic.ChangeNumberOfSeatsLogic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeNumberOfSeatsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangeNumberOfSeatsCommand.class);
    private static final String SPECIALITY = "speciality";
    private static final String NUMBER_OF_SEATS = "numberOfSeats";
    private ChangeNumberOfSeatsLogic logic;

    public ChangeNumberOfSeatsCommand(ChangeNumberOfSeatsLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String specialityValue = req.getParameter(SPECIALITY);
        String numberOfSeatsValue = req.getParameter(NUMBER_OF_SEATS);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);

        try {
            logic.doAction(specialityValue, numberOfSeatsValue);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=number of seats changed");
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + e);
        }

        return router;
    }
}
