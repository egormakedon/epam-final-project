package by.makedon.selectioncommittee.app.mail;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Yahor Makedon
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MailPropertyConstants {
    public static final String MAIL_RESOURCE_NAME = "mail";
    public static final String SMTP_HOST_PROPERTY_KEY = "mail.smtp.host";
    public static final String SMTP_PORT_PROPERTY_KEY = "mail.smtp.port";
    public static final String USER_NAME_PROPERTY_KEY = "mail.user.name";
    public static final String USER_PASSWORD_PROPERTY_KEY = "mail.user.password";
}
