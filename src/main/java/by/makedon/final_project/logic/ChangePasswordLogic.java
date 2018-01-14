package by.makedon.final_project.logic;

import by.makedon.final_project.dao.ChangePasswordDAO;
import by.makedon.final_project.dao.DAO;
import by.makedon.final_project.entity.User;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangePasswordLogic {
    private static final Logger LOGGER = LogManager.getLogger(ChangePasswordLogic.class);
    private static final String ERROR_MESSAGE = "input error";

    public String doAction(String usernameValue, String password1Value, String password2Value) {
        if (!(Validator.validatePassword(password1Value)&&Validator.arePasswordsEqual(password1Value, password2Value))) {
            return ERROR_MESSAGE;
        }
        DAO dao = ChangePasswordDAO.getInstance();
        String result;
        try {
            User user = new User();
            user.setUsernameValue(usernameValue);
            user.setPasswordValue(password1Value);
            dao.changePassword(user);
            result = "password changed successfully";
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            result = e.getMessage();
        }
        return result;
    }
}
