package by.makedon.selectioncommittee.app.logic;

public class LogicException extends Exception {
    public LogicException() {
    }
    public LogicException(String m) {
        super(m);
    }
    public LogicException(String m, Throwable th) {
        super(m, th);
    }
    public LogicException(Throwable th) {
        super(th);
    }
    public LogicException(String m, Throwable th, boolean enableSuppression, boolean writableStackTrace) {
        super(m, th, enableSuppression, writableStackTrace);
    }
}
