package by.makedon.final_project.logic.baselogic;

import by.makedon.final_project.dao.basedao.BaseDAO;
import by.makedon.final_project.dao.basedao.LoginDAO;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.validator.UserValidator;

public class LoginLogic {
    public boolean validate(String usernameValue, String passwordValue) {
        return UserValidator.validateUsername(usernameValue) && UserValidator.validatePassword(passwordValue);
    }

    public boolean match(String usernameValue, String passwordValue) throws DAOException {
        BaseDAO dao = LoginDAO.getInstance();
        return dao.match(usernameValue, passwordValue);
    }

    public String takeUserType(String usernameValue) throws DAOException {
        BaseDAO dao = LoginDAO.getInstance();
        return dao.takeUserType(usernameValue);
    }
}
