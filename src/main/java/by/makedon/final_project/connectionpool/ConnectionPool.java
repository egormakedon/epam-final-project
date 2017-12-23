package by.makedon.final_project.connectionpool;

import by.makedon.final_project.exception.FatalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool implements Cloneable {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private static AtomicBoolean instanceCreated;
    private static ReentrantLock lock = new ReentrantLock();
    private ConnectionPool() {
        throw new FatalException("Tried to clone singleton object with reflection api");
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (!instanceCreated.get()) {
                    instance = new ConnectionPool();
                    //////
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (instance != null) {
            throw new CloneNotSupportedException("Tried to clone singleton object");
        }
        return super.clone();
    }
}
