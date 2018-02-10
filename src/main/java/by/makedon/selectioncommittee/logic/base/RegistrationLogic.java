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
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        String templatePath = MailTemplatePath.REGISTRATION.getTemplatePath();
        MailBuilder mailBuilder = new MailBuilder(templatePath);
        String mailText = String.format(mailBuilder.takeMailTemplate(), emailValue, usernameValue, password1Value);

        MailThread mail = new MailThread(emailValue, CONFIRM_REGISTRATION, mailText, MailProperty.getInstance().getProperties());
        mail.start();
    }
}