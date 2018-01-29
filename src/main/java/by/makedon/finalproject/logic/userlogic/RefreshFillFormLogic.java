package by.makedon.finalproject.logic.userlogic;

import by.makedon.finalproject.constant.PageConstant;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.dao.userdao.UserDAO;
import by.makedon.finalproject.dao.userdao.UserDAOImpl;
import by.makedon.finalproject.exception.DAOException;

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
