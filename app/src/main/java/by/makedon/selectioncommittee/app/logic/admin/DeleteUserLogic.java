package by.makedon.selectioncommittee.app.logic.admin;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.dao.impl.AdminDaoImpl;
import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.dao.impl.UserDaoImpl;
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

public class DeleteUserLogic implements Logic {
    private static final int LIST_SIZE = 1;

    private static final String ADMIN = "admin";
    private static final String REMOVAL_NOTICE = "removal notice";

    @Override
    public void doAction(@NotNull List<String> parameters) throws LogicException {
        if (parameters.size() != LIST_SIZE) {
            throw new LogicException("wrong number of parameters");
        }

        String usernameValue = parameters.get(0);

        if (!UserValidator.validateUsername(usernameValue)) {
            throw new LogicException("invalid input parameters");
        }

        BaseDao baseDAO = BaseDaoImpl.getInstance();
        AdminDao adminDAO = AdminDaoImpl.getInstance();
        UserDao userDAO = UserDaoImpl.getInstance();
        try {
            if (!baseDAO.matchUsername(usernameValue)) {
                throw new LogicException("user doesn't exist");
            }

            String type = baseDAO.getTypeByUsername(usernameValue);
            if (type.equals(ADMIN)) {
                throw new LogicException("you can't delete this user");
            }

            String emailValue = userDAO.getEmailByUsername(usernameValue);

            String templatePath = MailTemplateType.DELETE_USER.getTemplatePath();
            MailBuilder mailBuilder = new MailBuilder(templatePath);
            String mailText = String.format(mailBuilder.takeMailTemplate(), usernameValue);

            MailSendTask mail = new MailSendTask(emailValue, REMOVAL_NOTICE, mailText, MailProperty.getInstance().getProperties());
            mail.start();

            adminDAO.deleteEnrolleeFormByUsername(usernameValue);
            adminDAO.deleteUserByUsername(usernameValue);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
