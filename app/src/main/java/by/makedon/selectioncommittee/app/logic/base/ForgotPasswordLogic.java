package by.makedon.selectioncommittee.app.logic.base;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.dao.DAOException;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.mail.MailBuilder;
import by.makedon.selectioncommittee.app.mail.MailProperty;
import by.makedon.selectioncommittee.app.mail.MailTemplateType;
import by.makedon.selectioncommittee.app.mail.MailSendTask;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ForgotPasswordLogic implements Logic {
    private static final int LIST_SIZE = 1;
    private static final String FORGOT_PASSWORD = "forgot password";

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String emailValue = parameters.get(0);

        if (!UserValidator.validateEmail(emailValue)) {
            throw new LogicException("invalid input parameters");
        }

        BaseDao dao = BaseDaoImpl.getInstance();
        String usernameValue;
        try {
            usernameValue = dao.getUsernameByEmail(emailValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        String templatePath = MailTemplateType.FORGOT_PASSWORD.getTemplatePath();
        MailBuilder mailBuilder = new MailBuilder(templatePath);
        String mailText = String.format(mailBuilder.takeMailTemplate(), usernameValue, usernameValue);

        MailSendTask thread = new MailSendTask(emailValue, FORGOT_PASSWORD, mailText, MailProperty.getInstance().getProperties());
        thread.start();
    }
}
