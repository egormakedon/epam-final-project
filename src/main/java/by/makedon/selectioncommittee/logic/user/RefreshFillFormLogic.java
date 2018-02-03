package by.makedon.selectioncommittee.logic.user;

import by.makedon.selectioncommittee.constant.Page;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.dao.user.UserDAO;
import by.makedon.selectioncommittee.dao.user.UserDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;

public class RefreshFillFormLogic {
    public Router doAction(String usernameValue) throws DAOException {
        UserDAO dao = UserDAOImpl.getInstance();
        dao.refreshFillForm(usernameValue);
        Router router = new Router();
        router.setPagePath(Page.MESSAGE + "?message=" + "form has refreshed successfully");
        router.setRoute(Router.RouteType.REDIRECT);
        return router;
    }
}
