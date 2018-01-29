package by.makedon.finalproject.command.usercommand;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.exception.LogicException;
import by.makedon.finalproject.logic.userlogic.ChangeUserDataLogic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeUserDataCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ChangeUserDataCommand.class);
    private static final String USERNAME = "username";
    private static final String TYPE_CHANGER = "typechanger";
    private ChangeUserDataLogic logic;


    public ChangeUserDataCommand(ChangeUserDataLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String usernameValue = (String)session.getAttribute(USERNAME);
        String typeChangerValue = (String)session.getAttribute(TYPE_CHANGER);

        Router router = new Router();
        router.setRoute(Router.RouteType.REDIRECT);
        try {
            logic.doAction(usernameValue, typeChangerValue);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=Check email");
        } catch (LogicException e) {
            LOGGER.log(Level.ERROR, e);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + e);
        }
        return router;
    }
}
