package by.makedon.finalproject.exception;

public class FatalException extends RuntimeException {
    public FatalException() {}
    public FatalException(String m) {
        super(m);
    }
    public FatalException(Throwable th) {
        super(th);
    }
    public FatalException(String m, Throwable th) {
        super(m, th);
    }
    public FatalException(String m, Throwable th, boolean suppression, boolean stackTrace) {
        super(m, th, suppression, stackTrace);
    }
}
