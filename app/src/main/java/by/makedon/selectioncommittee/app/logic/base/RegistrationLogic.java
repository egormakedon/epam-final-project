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

        BaseDao dao = BaseDaoImpl.getInstance();
        try {
            if (dao.matchEmailAndUsername(emailValue, usernameValue)) {
                throw new LogicException("this user already exist");
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        String templatePath = MailTemplateType.REGISTRATION.getTemplatePath();
        MailBuilder mailBuilder = new MailBuilder(templatePath);
        String mailText = String.format(mailBuilder.takeMailTemplate(), emailValue, usernameValue, password1Value);

        MailSendTask mail = new MailSendTask(emailValue, CONFIRM_REGISTRATION, mailText, MailProperty.getInstance().getProperties());
        mail.start();
    }
}
