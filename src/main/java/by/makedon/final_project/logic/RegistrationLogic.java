package by.makedon.final_project.logic;

import by.makedon.final_project.dao.DAO;
import by.makedon.final_project.dao.RegistrationDAO;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.validator.Validator;

public class RegistrationLogic {
    public boolean validate(String emailValue, String usernameValue, String password1Value, String password2Value) {
        return Validator.validateEmail(emailValue) && Validator.validateUsername(usernameValue) &&
                Validator.validatePassword(password1Value) && Validator.arePasswordsEqual(password1Value, password2Value);
    }

    public boolean addUser(String emailValue, String usernameValue, String passwordValue) throws DAOException {
        DAO dao = RegistrationDAO.getInstance();
        return dao.addUser(emailValue, usernameValue, passwordValue);
    }
}
