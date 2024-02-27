package by.makedon.selectioncommittee.common.resource;

/**
 * @author Yahor Makedon
 */
public class FileRegistrationException extends RuntimeException {
    public FileRegistrationException() {
        super();
    }

    public FileRegistrationException(String message) {
        super(message);
    }

    public FileRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileRegistrationException(Throwable cause) {
        super(cause);
    }
}
