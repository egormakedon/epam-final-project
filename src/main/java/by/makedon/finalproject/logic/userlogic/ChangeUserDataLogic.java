package by.makedon.finalproject.logic.userlogic;

import by.makedon.finalproject.dao.userdao.UserDAO;
import by.makedon.finalproject.dao.userdao.UserDAOImpl;
import by.makedon.finalproject.exception.DAOException;
import by.makedon.finalproject.exception.LogicException;
import by.makedon.finalproject.mail.MailProperty;
import by.makedon.finalproject.mail.MailThread;

public class ChangeUserDataLogic {
    private static final String CHANGE_USER_DATA = "change user data";

    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public void doAction(String usernameValue, String typeChangerValue) throws LogicException {
        String emailValue;
        try {
            emailValue = takeUserEmailValue(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        String mailText = mailTextCreator(usernameValue, typeChangerValue);
        MailThread thread = new MailThread(emailValue, CHANGE_USER_DATA, mailText, MailProperty.getInstance().getProperties());
        thread.start();
    }

    private String takeUserEmailValue(String usernameValue) throws DAOException {
        UserDAO dao = UserDAOImpl.getInstance();
        return dao.takeEmail(usernameValue);
    }
    private String mailTextCreator(String usernameValue, String typeChangerValue) throws LogicException {
        String mailText;
        switch (typeChangerValue) {
            case EMAIL:
                mailText = "<form action=\"http://localhost:8080/Controller\" method=\"post\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"username\" value=\""+usernameValue+"\">\n" +
                        "\t\t\t\t\t<input type=\"hidden\" name=\"command\" value=\"changeuserdatalink\">\n" +
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
                        "\t\t\t\t\t<input type=\"hidden\" name=\"command\" value=\"changeuserdatalink\">\n" +
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
                        "\t\t\t\t\t<input type=\"hidden\" name=\"command\" value=\"changeuserdatalink\">\n" +
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
                throw new LogicException("ChangeUserDataLogic exception in mail text creator");
        }
        return mailText;
    }
}
