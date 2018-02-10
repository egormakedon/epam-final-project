package by.makedon.selectioncommittee.logic.base;

import by.makedon.selectioncommittee.dao.base.BaseDAO;
import by.makedon.selectioncommittee.dao.base.BaseDAOImpl;
import by.makedon.selectioncommittee.exception.DAOException;
import by.makedon.selectioncommittee.exception.LogicException;
import by.makedon.selectioncommittee.logic.Logic;
import by.makedon.selectioncommittee.mail.MailBuilder;
import by.makedon.selectioncommittee.mail.MailProperty;
import by.makedon.selectioncommittee.mail.MailTemplatePath;
import by.makedon.selectioncommittee.mail.MailThread;
import by.makedon.selectioncommittee.validator.UserValidator;
import com.sun.istack.internal.NotNull;
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

        BaseDAO dao = BaseDAOImpl.getInstance();
        String usernameValue;
        try {
            usernameValue = dao.takeUsernameByEmail(emailValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        String templatePath = MailTemplatePath.FORGOT_PASSWORD.getTemplatePath();
        MailBuilder mailBuilder = new MailBuilder(templatePath);
        String mailText = String.format(mailBuilder.takeMailTemplate(), usernameValue, usernameValue);

        MailThread thread = new MailThread(emailValue, FORGOT_PASSWORD, mailText, MailProperty.getInstance().getProperties());
        thread.start();
    }
}