package by.makedon.final_project.mail;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger(MailThread.class);
    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    private Properties properties;

    public MailThread(String sendToEmail, String mailSubject, String mailText, Properties properties) {
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
