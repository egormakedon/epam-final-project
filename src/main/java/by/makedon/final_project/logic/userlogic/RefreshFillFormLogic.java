package by.makedon.final_project.logic.userlogic;

import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.dao.userdao.UserDAO;
import by.makedon.final_project.dao.userdao.UserDAOImpl;
import by.makedon.final_project.exception.DAOException;

public class RefreshFillFormLogic {
    public Router doAction(String usernameValue) throws DAOException {
        UserDAO dao = UserDAOImpl.getInstance();
        dao.refreshFillForm(usernameValue);
        Router router = new Router();
        router.setPagePath(PageConstant.MESSAGE_PAGE + "?message=" + "form has refreshed successfully");
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
