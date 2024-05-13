package by.makedon.selectioncommittee.app.logic.user;

import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE;
import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.USER_NOT_EXIST_WITH_USERNAME;

public class ChangeUserDataLogic implements Logic {
    private static final int VALID_PARAMETERS_SIZE = 2;
    private static final String MAIL_SUBJECT_CHANGE_USER_DATA_NOTIFICATION = "Change user data notification";
    private static final String CHANGE_EMAIL = "change email";
    private static final String CHANGE_USERNAME = "change username";
    private static final String CHANGE_PASSWORD = "change password";

    private final UserDao userDao = UserDaoImpl.getInstance();
    private final BaseDao baseDao = BaseDaoImpl.getInstance();
    private final UserValidator userValidator = new UserValidator();

    private final Map<String, Function<String, String>> typeChangerToMailContentByUsernameFunctionMap;

    {
        String mailTemplate = MailTemplateFactory.getMailTemplateBy(MailTemplateType.CHANGE_USER_DATA);

        typeChangerToMailContentByUsernameFunctionMap = new HashMap<>();
        typeChangerToMailContentByUsernameFunctionMap.put(RequestParameterKey.EMAIL,
            username -> String.format(mailTemplate, username, RequestParameterKey.EMAIL, CHANGE_EMAIL));
        typeChangerToMailContentByUsernameFunctionMap.put(RequestParameterKey.USERNAME,
            username -> String.format(mailTemplate, username, RequestParameterKey.USERNAME, CHANGE_USERNAME));
        typeChangerToMailContentByUsernameFunctionMap.put(RequestParameterKey.PASSWORD,
            username -> String.format(mailTemplate, username, RequestParameterKey.PASSWORD, CHANGE_PASSWORD));
    }

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

        String typeChangerValue = parameters.get(1);
        if (!typeChangerToMailContentByUsernameFunctionMap.containsKey(typeChangerValue)) {
            final String message = String.format(
                INVALID_INPUT_PARAMETER_WITH_PARAMETER_VALUE, "typeChanger", typeChangerValue);
            throw new ValidationException(message);
        }
    }

    @Override
    public void action(@NotNull List<String> parameters) throws DaoException, LogicException {
        String username = parameters.get(0);
        String typeChanger = parameters.get(1);
        sendNotificationEmail(username, typeChanger);
    }

    private void sendNotificationEmail(String username, String typeChanger) {
        String email = userDao.getEmailByUsername(username);
        String content = typeChangerToMailContentByUsernameFunctionMap
            .get(typeChanger)
            .apply(username);

        Mail mail = new Mail(email, MAIL_SUBJECT_CHANGE_USER_DATA_NOTIFICATION, content);
        MailSendTask mailSendTask = new MailSendTask(mail);
        CompletableFuture.runAsync(mailSendTask);
    }
}
