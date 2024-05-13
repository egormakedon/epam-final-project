package by.makedon.selectioncommittee.common.resource;

/**
 * @author Yahor Makedon
 */
public class ResourceMissingException extends RuntimeException {
    public ResourceMissingException() {
        super();
    }

    public ResourceMissingException(String message) {
        super(message);
    }

    public ResourceMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceMissingException(Throwable cause) {
        super(cause);
    }
}
