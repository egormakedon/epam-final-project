package by.makedon.selectioncommittee.common.connectionpool;

/**
 * @author Yahor Makedon
 */
public class ConnectionPoolInitializationException extends RuntimeException {
    public ConnectionPoolInitializationException() {
        super();
    }

    public ConnectionPoolInitializationException(String message) {
        super(message);
    }

    public ConnectionPoolInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolInitializationException(Throwable cause) {
        super(cause);
    }
}
