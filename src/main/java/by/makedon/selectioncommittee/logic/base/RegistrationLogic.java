package by.makedon.selectioncommittee.logic.base;

import by.makedon.selectioncommittee.dao.base.BaseDAO;
import by.makedon.selectioncommittee.dao.base.BaseDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.mail.MailProperty;
import by.makedon.selectioncommittee.mail.MailThread;
import by.makedon.selectioncommittee.validator.UserValidator;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class RegistrationLogic implements Logic {
    private static final int LIST_SIZE = 4;

    private static final String CONFIRM_REGISTRATION = "confirm registration";

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String emailValue = parameters.get(0);
        String usernameValue = parameters.get(1);
        String password1Value = parameters.get(2);
        String password2Value = parameters.get(3);

        if (!(UserValidator.validateEmail(emailValue) && UserValidator.validateUsername(usernameValue) &&
                UserValidator.validatePassword(password1Value) && UserValidator.arePasswordsEqual(password1Value, password2Value))) {
            throw new LogicException("invalid input parameters");
        }

        BaseDAO dao = BaseDAOImpl.getInstance();
        try {
            if (dao.matchEmailUsername(emailValue, usernameValue)) {
                throw new LogicException("this user already exist");
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

            MailThread mail = new MailThread(emailValue, CONFIRM_REGISTRATION, mailText, MailProperty.getInstance().getProperties());
            mail.start();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
