package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.mail.MailBuilder;
import by.makedon.selectioncommittee.app.mail.MailProperty;
import by.makedon.selectioncommittee.app.mail.MailTemplateType;
import by.makedon.selectioncommittee.app.mail.MailSendTask;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChangeUserDataLogic implements Logic {
    private static final int LIST_SIZE = 2;

    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String CHANGE_USER_DATA = "change user data";
    private static final String CHANGE_EMAIL = "change email";
    private static final String CHANGE_USERNAME = "change username";
    private static final String CHANGE_PASSWORD = "change password";

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);
        String typeChangerValue = parameters.get(1);

        UserDao dao = UserDaoImpl.getInstance();
        String emailValue;
        try {
            emailValue = dao.getEmailByUsername(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        String templatePath = MailTemplateType.CHANGE_USER_DATA.getTemplatePath();
        MailBuilder mailBuilder = new MailBuilder(templatePath);
        String mailText = mailTextCreator(mailBuilder, usernameValue, typeChangerValue);

        MailSendTask thread = new MailSendTask(emailValue, CHANGE_USER_DATA, mailText, MailProperty.getInstance().getProperties());
        thread.start();
    }

    private String mailTextCreator(MailBuilder mailBuilder, String usernameValue, String typeChangerValue) throws LogicException {
        String mailText;
        switch (typeChangerValue) {
            case EMAIL:
                mailText = String.format(mailBuilder.takeMailTemplate(), usernameValue, typeChangerValue, CHANGE_EMAIL);
                break;
            case USERNAME:
                mailText = String.format(mailBuilder.takeMailTemplate(), usernameValue, typeChangerValue, CHANGE_USERNAME);
                break;
            case PASSWORD:
                mailText = String.format(mailBuilder.takeMailTemplate(), usernameValue, typeChangerValue, CHANGE_PASSWORD);
                break;
            default:
                throw new LogicException("invalid parameter");
        }
        return mailText;
    }
}
