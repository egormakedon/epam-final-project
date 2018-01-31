package by.makedon.selectioncommittee.logic.userlogic;

import by.makedon.selectioncommittee.constant.PageConstant;
import by.makedon.selectioncommittee.controller.Router;
import by.makedon.selectioncommittee.dao.userdao.UserDAO;
import by.makedon.selectioncommittee.dao.userdao.UserDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;

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
