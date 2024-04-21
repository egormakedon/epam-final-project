package by.makedon.selectioncommittee.app.dao;

public class DAOException extends Exception {
    public DAOException() {}
    public DAOException(String m) {
        super(m);
    }
    public DAOException(Throwable th) {
        super(th);
    }
    public DAOException(String m, Throwable th) {
        super(m, th);
    }
    public DAOException(String m, Throwable th, boolean suppression, boolean stackTrace) {
        super(m, th, suppression, stackTrace);
    }
}
