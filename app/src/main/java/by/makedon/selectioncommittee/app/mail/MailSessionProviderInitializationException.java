package by.makedon.selectioncommittee.app.mail;

/**
 * @author Yahor Makedon
 */
public final class MailSessionProviderInitializationException extends RuntimeException {
    public MailSessionProviderInitializationException() {
    }

    public MailSessionProviderInitializationException(String message) {
        super(message);
    }

    public MailSessionProviderInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailSessionProviderInitializationException(Throwable cause) {
        super(cause);
    }
}
