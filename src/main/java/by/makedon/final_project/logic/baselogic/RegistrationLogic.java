package by.makedon.final_project.logic.baselogic;

import by.makedon.final_project.dao.basedao.BaseDAO;
import by.makedon.final_project.dao.basedao.RegistrationDAO;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.mail.MailProperty;
import by.makedon.final_project.mail.MailThread;
import by.makedon.final_project.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationLogic {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationLogic.class);
    private static final String INPUT_ERROR_MESSAGE = "input error";
    private static final String ACCEPT_REGISTRATION_MESSAGE = "confirm of registration sent to the email";
    private static final String MAIL_SUBJECT = "confirm registration";

    public String doAction(String emailValue, String usernameValue, String password1Value, String password2Value) {
        if (!(Validator.validateEmail(emailValue) && Validator.validateUsername(usernameValue) &&
                Validator.validatePassword(password1Value) && Validator.arePasswordsEqual(password1Value, password2Value))) {
            return INPUT_ERROR_MESSAGE;
        }
        BaseDAO dao = RegistrationDAO.getInstance();
        try {
            if (dao.match(emailValue, usernameValue)) {
                return emailValue + " " + usernameValue + " already exist";
            }
            String mailText = "<form action=\"http://localhost:8080/Controller\" method=\"post\">\n" +
                    "\t\t\t\t\t<input type=\"hidden\" name=\"email\" value=\""+emailValue+"\">\n" +
                    "\t\t\t\t\t<input type=\"hidden\" name=\"username\" value=\""+usernameValue+"\">\n" +
                    "\t\t\t\t\t<input type=\"hidden\" name=\"password\" value=\""+password1Value+"\">\n" +
                    "\t\t\t\t\t<input type=\"hidden\" name=\"command\" value=\"acceptregistration\">\n" +
                    "\t\t\t\t\t<input type=\"submit\" style=\"background:none!important;\n" +
                    "     color:purple;\n" +
                    "     border:none; \n" +
                    "     padding:0!important;\n" +
                    "     font: inherit;\n" +
                    "     border-bottom:1px solid #444; \n" +
                    "     cursor: pointer;\" value=\"accept registration\">\n" +
                    "\t\t\t\t</form>";
            MailThread mail = new MailThread(emailValue, MAIL_SUBJECT, mailText, MailProperty.getInstance().getProperties());
            mail.start();
            return ACCEPT_REGISTRATION_MESSAGE;
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
            return e.getMessage();
        }
    }
}
