package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
import by.makedon.selectioncommittee.app.entity.UserType;
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

import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.*;

public class DeleteUserLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 1;
    private static final String MAIL_SUBJECT_USER_REMOVAL_NOTIFICATION = "User removal notification";

    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final AdminDao adminDao = AdminDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public int getValidParametersSize() {
        return VALID_PARAMETERS_SIZE;
    }

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        String usernameValue = parameters.get(0);

        if (!userValidator.validateUsername(usernameValue)) {
            final String message = String.format(
                INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE, "username", usernameValue);
            throw new ValidationException(message);
        }

        if (!baseDao.matchUsername(usernameValue)) {
            final String message = String.format(USER_NOT_EXIST_WITH_USERNAME, usernameValue);
            throw new ValidationException(message);
        }

        String type = baseDao.getTypeByUsername(usernameValue);
        UserType userType = UserType.of(type);
        if (userType.isAdmin()) {
            final String message = String.format(ACTION_DENIED_WITH_NAME_USERNAME, "removal", usernameValue);
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String username = parameters.get(0);
        removeUserBy(username);
        sendNotificationEmail(username);
    }

    private void removeUserBy(String username) {
        adminDao.deleteEnrolleeFormByUsername(username);
        adminDao.deleteUserByUsername(username);
    }

    private void sendNotificationEmail(String username) {
        String email = userDao.getEmailByUsername(username);
        String mailTemplate = MailTemplateFactory.getMailTemplateBy(MailTemplateType.DELETE_USER);
        String content = String.format(mailTemplate, username);

        Mail mail = new Mail(email, MAIL_SUBJECT_USER_REMOVAL_NOTIFICATION, content);
        MailSendTask mailSendTask = new MailSendTask(mail);
        CompletableFuture.runAsync(mailSendTask);
    }
}
