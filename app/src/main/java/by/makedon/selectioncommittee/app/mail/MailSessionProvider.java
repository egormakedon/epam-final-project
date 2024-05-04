package by.makedon.selectioncommittee.app.mail;

import by.makedon.selectioncommittee.common.resource.PropertyHolder;
import by.makedon.selectioncommittee.common.resource.ResourceUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

import static by.makedon.selectioncommittee.app.mail.MailPropertyConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MailSessionProvider {
    private static final String username;
    private static final String password;
    private static final Properties properties;

    static {
        PropertyHolder mailResource = ResourceUtil.resource(MAIL_RESOURCE_NAME);
        final String messageTemplate = "Required property `%s` is missed";

        username = mailResource
            .property(USER_NAME_PROPERTY_KEY)
            .orElseThrow(() -> new MailSessionProviderInitializationException(String.format(messageTemplate, USER_NAME_PROPERTY_KEY)));
        password = mailResource
            .property(USER_PASSWORD_PROPERTY_KEY)
            .orElseThrow(() -> new MailSessionProviderInitializationException(String.format(messageTemplate, USER_PASSWORD_PROPERTY_KEY)));

        String smtpHost = mailResource
            .property(SMTP_HOST_PROPERTY_KEY)
            .orElseThrow(() -> new MailSessionProviderInitializationException(String.format(messageTemplate, SMTP_HOST_PROPERTY_KEY)));
        String smtpPort = mailResource
            .property(SMTP_PORT_PROPERTY_KEY)
            .orElseThrow(() -> new MailSessionProviderInitializationException(String.format(messageTemplate, SMTP_PORT_PROPERTY_KEY)));

        properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", smtpHost);
        properties.setProperty("mail.smtp.auth", Boolean.TRUE.toString());
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", Boolean.FALSE.toString());
        properties.setProperty("mail.smtp.socketFactory.fallback", Boolean.FALSE.toString());
    }

    public static Session getSession() {
        return Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
