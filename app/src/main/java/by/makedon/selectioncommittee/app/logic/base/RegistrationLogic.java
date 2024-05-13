package by.makedon.selectioncommittee.app.logic.base;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.mail.Mail;
import by.makedon.selectioncommittee.app.mail.MailSendTask;
import by.makedon.selectioncommittee.app.mail.MailTemplateFactory;
import by.makedon.selectioncommittee.app.mail.MailTemplateType;
import by.makedon.selectioncommittee.app.validator.UserValidator;
import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE;
import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.USER_EXISTS_WITH_EMAIL_USERNAME;

public class RegistrationLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 4;
    private static final String MAIL_SUBJECT_CONFIRMATION_REGISTRATION_NOTIFICATION = "Confirmation registration notification";

    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public int getValidParametersSize() {
        return VALID_PARAMETERS_SIZE;
    }

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        String emailValue = parameters.get(0);
        String usernameValue = parameters.get(1);
        String password1Value = parameters.get(2);
        String password2Value = parameters.get(3);

        if (!userValidator.validateEmail(emailValue)) {
            final String message = String.format(INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE, "email", emailValue);
            throw new ValidationException(message);
        }

        if (!userValidator.validateUsername(usernameValue)) {
            final String message = String.format(INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE, "username", usernameValue);
            throw new ValidationException(message);
        }

        if (!(userValidator.validatePassword(password1Value) && userValidator.arePasswordsEqual(password1Value, password2Value))) {
            final String message = "Invalid input password1 or password2";
            throw new ValidationException(message);
        }

        if (baseDao.matchEmailAndUsername(emailValue, usernameValue)) {
            final String message = String.format(USER_EXISTS_WITH_EMAIL_USERNAME, emailValue, usernameValue);
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String email = parameters.get(0);
        String username = parameters.get(1);
        String password = parameters.get(2);

        sendNotificationEmail(email, username, password);
    }

    private void sendNotificationEmail(String email, String username, String password) {
        String mailTemplate = MailTemplateFactory.getMailTemplateBy(MailTemplateType.REGISTRATION);
        String content = String.format(mailTemplate, email, username, password);

        Mail mail = new Mail(email, MAIL_SUBJECT_CONFIRMATION_REGISTRATION_NOTIFICATION, content);
        MailSendTask mailSendTask = new MailSendTask(mail);
        CompletableFuture.runAsync(mailSendTask);
    }
}
