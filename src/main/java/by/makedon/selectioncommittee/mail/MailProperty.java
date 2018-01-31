package by.makedon.selectioncommittee.mail;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class MailProperty {
    private static final Logger LOGGER = LogManager.getLogger(MailProperty.class);
    private static MailProperty instance;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static Properties properties = new Properties();

    private MailProperty() {}

    public static MailProperty getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new MailProperty();
                    init();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Properties getProperties() {
        return properties;
    }

    private static void init() {
        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle("config.mail");
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "Hasn't mail.properties");
            throw new RuntimeException("Hasn't mail.properties");
        }
        properties.put("mail.smtp.host", resourceBundle.getString("mail.smtp.host"));
        properties.put("mail.smtp.port", resourceBundle.getString("mail.smtp.port"));
        properties.put("mail.user.name", resourceBundle.getString("mail.user.name"));
        properties.put("mail.user.password", resourceBundle.getString("mail.user.password"));
    }
}