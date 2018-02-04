package by.makedon.selectioncommittee.logic.user;

import by.makedon.selectioncommittee.dao.user.UserDAO;
import by.makedon.selectioncommittee.dao.user.UserDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.mail.MailProperty;
import by.makedon.selectioncommittee.mail.MailThread;
import com.sun.istack.internal.NotNull;

import java.util.List;

public class ChangeUserDataLogic implements Logic {
    private static final int LIST_SIZE = 2;

    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CHANGE_USER_DATA = "change user data";

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);
        String typeChangerValue = parameters.get(1);

        UserDAO dao = UserDAOImpl.getInstance();
        String emailValue;
        try {
            emailValue = dao.takeEmailByUsername(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        String mailText = mailTextCreator(usernameValue, typeChangerValue);
        MailThread thread = new MailThread(emailValue, CHANGE_USER_DATA, mailText, MailProperty.getInstance().getProperties());
        thread.start();
    }

    private String mailTextCreator(String usernameValue, String typeChangerValue) throws LogicException {
        String mailText;
        switch (typeChangerValue) {
            case EMAIL:
                mailText = "<form action=\"http://localhost:8080/Controller\" method=\"post\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"username\" value=\""+usernameValue+"\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"command\" value=\"forwardchangeuserdatalink\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"typechanger\" value=\"email\">\n" +
                        "\t\t\t\t\t<input type=\"submit\" style=\"background:none!important;\n" +
                        "     color:purple;\n" +
                        "     border:none; \n" +
                        "     padding:0!important;\n" +
                        "     font: inherit;\n" +
                        "     border-bottom:1px solid #444; \n" +
                        "     cursor: pointer;\" value=\"change email\">\n" +
                        "\t\t\t\t</form>";
                break;
            case USERNAME:
                mailText = "<form action=\"http://localhost:8080/Controller\" method=\"post\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"username\" value=\""+usernameValue+"\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"command\" value=\"forwardchangeuserdatalink\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"typechanger\" value=\"username\">\n" +
                        "\t\t\t\t\t<input type=\"submit\" style=\"background:none!important;\n" +
                        "     color:purple;\n" +
                        "     border:none; \n" +
                        "     padding:0!important;\n" +
                        "     font: inherit;\n" +
                        "     border-bottom:1px solid #444; \n" +
                        "     cursor: pointer;\" value=\"change username\">\n" +
                        "\t\t\t\t</form>";
                break;
            case PASSWORD:
                mailText = "<form action=\"http://localhost:8080/Controller\" method=\"post\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"username\" value=\""+usernameValue+"\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"command\" value=\"forwardchangeuserdatalink\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"typechanger\" value=\"password\">\n" +
                        "\t\t\t\t\t<input type=\"submit\" style=\"background:none!important;\n" +
                        "     color:purple;\n" +
                        "     border:none; \n" +
                        "     padding:0!important;\n" +
                        "     font: inherit;\n" +
                        "     border-bottom:1px solid #444; \n" +
                        "     cursor: pointer;\" value=\"change password\">\n" +
                        "\t\t\t\t</form>";
                break;
            default:
                throw new LogicException("invalid parameter");
        }
        return mailText;
    }
}