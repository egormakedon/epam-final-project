package by.makedon.selectioncommittee.app.logic;

public final class LogicException extends RuntimeException {
    public LogicException() {
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }
}
