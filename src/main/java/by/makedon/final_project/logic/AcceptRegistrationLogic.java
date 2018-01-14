package by.makedon.final_project.logic;

import by.makedon.final_project.dao.DAO;
import by.makedon.final_project.dao.RegistrationDAO;
import by.makedon.final_project.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AcceptRegistrationLogic {
    private static final Logger LOGGER = LogManager.getLogger(AcceptRegistrationLogic.class);
    public String doAction(String emailValue, String usernameValue, String password) {
        DAO dao = RegistrationDAO.getInstance();
        try {
            dao.addUser(emailValue, usernameValue, password);
            return usernameValue + " register successfully";
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            return e.getMessage();
        }
    }
}
