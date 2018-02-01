package by.makedon.selectioncommittee.logic.base;

import by.makedon.selectioncommittee.dao.base.ChangePasswordDAO;
import by.makedon.selectioncommittee.dao.base.BaseDAO;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.mail.MailProperty;
import by.makedon.selectioncommittee.mail.MailThread;
import by.makedon.selectioncommittee.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ForgotPasswordLogic {
    private static final Logger LOGGER = LogManager.getLogger(ForgotPasswordLogic.class);
    private static final String FORGOT_PASSWORD = "forgot password";

    public void doAction(String emailValue) {
        if (!UserValidator.validateEmail(emailValue)) {
            LOGGER.log(Level.ERROR, "invalid email");
            return;
        }

        BaseDAO dao = ChangePasswordDAO.getInstance();
        try {
            String usernameValue = dao.takeUsername(emailValue);
            String mailText = "your username: " + usernameValue + "<br>"+
                    "<form action=\"http://localhost:8080/Controller\" method=\"post\">\n" +
                    "\t\t\t\t\t<input type=\"hidden\" name=\"username\" value=\""+usernameValue+"\">\n" +
                    "\t\t\t\t\t<input type=\"hidden\" name=\"command\" value=\"forwardchangepassword\">\n" +
                    "\t\t\t\t\t<input type=\"submit\" style=\"background:none!important;\n" +
                    "     color:purple;\n" +
                    "     border:none; \n" +
                    "     padding:0!important;\n" +
                    "     font: inherit;\n" +
                    "     border-bottom:1px solid #444; \n" +
                    "     cursor: pointer;\" value=\"change password\">\n" +
                    "\t\t\t\t</form>";
            MailThread thread = new MailThread(emailValue, FORGOT_PASSWORD, mailText, MailProperty.getInstance().getProperties());
            thread.start();
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
