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

public class ForgotPasswordLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 1;
    private static final String MAIL_SUBJECT_FORGOT_PASSWORD_NOTIFICATION = "Forgot password notification";

    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        if (parameters.size() != VALID_PARAMETERS_SIZE) {
            final String message = String.format(
                "Invalid input parameters size: expected=`%d`, actual=`%d`", VALID_PARAMETERS_SIZE, parameters.size());
            throw new ValidationException(message);
        }

        String emailValue = parameters.get(0);
        if (!userValidator.validateEmail(emailValue)) {
            final String message = String.format("Invalid input email parameter: `%s`", emailValue);
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String email = parameters.get(0);
        String username = baseDao.getUsernameByEmail(email);
        sendNotificationEmail(email, username);
    }

    private void sendNotificationEmail(String email, String username) {
        String mailTemplate = MailTemplateFactory.getMailTemplateBy(MailTemplateType.FORGOT_PASSWORD);
        String content = String.format(mailTemplate, username, username);

        Mail mail = new Mail(email, MAIL_SUBJECT_FORGOT_PASSWORD_NOTIFICATION, content);
        MailSendTask mailSendTask = new MailSendTask(mail);
        CompletableFuture.runAsync(mailSendTask);
    }
}
