package by.makedon.final_project.logic.userlogic;

import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.dao.userdao.UserDAO;
import by.makedon.final_project.dao.userdao.UserDAOImpl;
import by.makedon.final_project.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FillFormLogic {
    private static final Logger LOGGER = LogManager.getLogger(FillFormLogic.class);
    public Router doAction(String usernameValue) {
        UserDAO dao = UserDAOImpl.getInstance();
        Router router = new Router();
        try {
            if(dao.isFormFilled(usernameValue)) {
                router.setRoute(Router.RouteType.FORWARD);
                //router.setPagePath(PageConstant.CHECK_STATUS_PAGE);
                return router;
            } else {
                router.setRoute(Router.RouteType.FORWARD);
                router.setPagePath(PageConstant.FILL_FORM_PAGE);
                return router;
            }
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            router.setRoute(Router.RouteType.REDIRECT);
            router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + e.getMessage());
            return router;
        }
    }
}
