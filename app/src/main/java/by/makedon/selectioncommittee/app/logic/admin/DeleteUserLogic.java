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

public class DeleteUserLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 1;
    private static final String MAIL_SUBJECT_USER_REMOVAL_NOTIFICATION = "User removal notification";

    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final AdminDao adminDao = AdminDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public void validate(@NotNull List<String> parameters) throws ValidationException {
        if (parameters.size() != VALID_PARAMETERS_SIZE) {
            final String message = String.format(
                "Invalid input parameters size: expected=`%d`, actual=`%d`", VALID_PARAMETERS_SIZE, parameters.size());
            throw new ValidationException(message);
        }

        String usernameValue = parameters.get(0);

        if (!userValidator.validateUsername(usernameValue)) {
            final String message = String.format("Invalid input username parameter: `%s`", usernameValue);
            throw new ValidationException(message);
        }

        if (!baseDao.matchUsername(usernameValue)) {
            final String message = String.format("User with username: `%s` does not exist", usernameValue);
            throw new ValidationException(message);
        }

        String type = baseDao.getTypeByUsername(usernameValue);
        UserType userType = UserType.of(type);
        if (userType.isAdmin()) {
            final String message = String.format("Removal action for user: `%s` has been denied!!", usernameValue);
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
