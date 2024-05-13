package by.makedon.selectioncommittee.app.mail;

/**
 * @author Yahor Makedon
 */
public final class MailTemplateReaderException extends RuntimeException {
    public MailTemplateReaderException() {
    }

    public MailTemplateReaderException(String message) {
        super(message);
    }

    public MailTemplateReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailTemplateReaderException(Throwable cause) {
        super(cause);
    }
}
