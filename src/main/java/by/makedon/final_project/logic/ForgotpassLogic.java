package by.makedon.final_project.logic;

import by.makedon.final_project.dao.DAO;
import by.makedon.final_project.dao.MailDAO;
import by.makedon.final_project.exception.DAOException;
import by.makedon.final_project.exception.FatalException;
import by.makedon.final_project.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ForgotpassLogic {
    private static final Logger LOGGER = LogManager.getLogger(ForgotpassLogic.class);
    private static final String FORGOT_PASSWORD = "forgot password";

    private class MailThread extends Thread {
        private MimeMessage message;
        private String sendToEmail;
        private String mailSubject;
        private String mailText;
        private Properties properties;

        MailThread(String sendToEmail, String mailSubject, String mailText, Properties properties) {
            this.sendToEmail = sendToEmail;
            this.mailSubject = mailSubject;
            this.mailText = mailText;
            this.properties = properties;
        }

        @Override
        public void run() {
            init();
            try {
                Transport.send(message);
            } catch (MessagingException e) {
                LOGGER.log(Level.ERROR,"error of sending", e);
            }
        }

        private void init() {
            Session mailSession = (new SessionCreator(properties)).createSession();
            mailSession.setDebug(true);
            message = new MimeMessage(mailSession);
            try {
                message.setSubject(mailSubject);
                message.setContent(mailText, "text/html");
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
            } catch (AddressException e) {
                LOGGER.log(Level.ERROR,"incorrect email:" + sendToEmail, e);
            } catch (MessagingException e) {
                LOGGER.log(Level.ERROR,"error of formed message", e);
            }
        }
    }
    private class SessionCreator {
        private String smtpHost;
        private String smtpPort;
        private String userName;
        private String userPassword;
        private Properties sessionProperties;

        SessionCreator(Properties configProperties) {
            smtpHost = configProperties.getProperty("mail.smtp.host");
            smtpPort = configProperties.getProperty("mail.smtp.port");
            userName = configProperties.getProperty("mail.user.name");
            userPassword = configProperties.getProperty("mail.user.password");
            sessionProperties = new Properties();
            sessionProperties.setProperty("mail.transport.protocol", "smtp");
            sessionProperties.setProperty("mail.host", smtpHost);
            sessionProperties.put("mail.smtp.auth", "true");
            sessionProperties.put("mail.smtp.port", smtpPort);
            sessionProperties.put("mail.smtp.socketFactory.port", smtpPort);
            sessionProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            sessionProperties.put("mail.smtp.socketFactory.fallback", "false");
            sessionProperties.setProperty("mail.smtp.quitwait", "false");
        }

        public Session createSession() {
            return Session.getDefaultInstance(sessionProperties, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, userPassword);
                        }
                    });
        }
    }

    public void doAction(String emailValue) {
        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle("config.mail");
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "Hasn't mail.properties");
            throw new FatalException("Hasn't mail.properties");
        }

        Properties properties = new Properties();
        properties.put("mail.smtp.host", resourceBundle.getString("mail.smtp.host"));
        properties.put("mail.smtp.port", resourceBundle.getString("mail.smtp.port"));
        properties.put("mail.user.name", resourceBundle.getString("mail.user.name"));
        properties.put("mail.user.password", resourceBundle.getString("mail.user.password"));

        if (!Validator.validateEmail(emailValue)) {
            LOGGER.log(Level.ERROR, "invalid email");
            return;
        }

        DAO dao = MailDAO.getInstance();
        try {
            String usernameValue = dao.takeUsername(emailValue);
            String mailText = "http://127.0.0.1/jsp/welcome.jsp";
            MailThread thread = new MailThread(emailValue, FORGOT_PASSWORD, mailText, properties);
            thread.start();
        } catch (DAOException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
