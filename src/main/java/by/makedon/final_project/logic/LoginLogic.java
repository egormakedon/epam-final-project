package by.makedon.final_project.logic;

import by.makedon.final_project.dao.DAO;
import by.makedon.final_project.dao.LoginDAO;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.validator.Validator;

public class LoginLogic {
    public boolean validate(String usernameValue, String passwordValue) {
        return Validator.validateUsername(usernameValue) && Validator.validatePassword(passwordValue);
    }

    public boolean match(String usernameValue, String passwordValue) throws DAOException {
        DAO dao = LoginDAO.getInstance();
        return dao.match(usernameValue, passwordValue);
    }

    public String takeUserType(String usernameValue) throws DAOException {
        DAO dao = LoginDAO.getInstance();
        return dao.takeUserType(usernameValue);
    }
}
