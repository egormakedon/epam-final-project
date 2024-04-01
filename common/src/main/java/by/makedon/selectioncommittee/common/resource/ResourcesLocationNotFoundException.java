package by.makedon.selectioncommittee.common.resource;

/**
 * @author Yahor Makedon
 */
public final class ResourcesLocationNotFoundException extends RuntimeException {
    public ResourcesLocationNotFoundException() {
        super();
    }

    public ResourcesLocationNotFoundException(String message) {
        super(message);
    }

    public ResourcesLocationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourcesLocationNotFoundException(Throwable cause) {
        super(cause);
    }
}
