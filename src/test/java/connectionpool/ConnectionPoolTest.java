package connectionpool;

import by.makedon.selectioncommittee.connectionpool.ConnectionPool;
import by.makedon.selectioncommittee.connectionpool.ProxyConnection;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConnectionPoolTest {

    @Test (expectedExceptions = InvocationTargetException.class)
    public void checkDefenceFromReflectionApi() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        ConnectionPool.getInstance();
        Constructor[] constructors = ConnectionPool.class.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            constructor.setAccessible(true);
            constructor.newInstance();
        }
    }

    @Test
    public void checkOnDestroyConnections() throws SQLException, InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        (new Thread() {
            @Override
            public void run() {
                List<ProxyConnection> connections = new ArrayList<ProxyConnection>();
                for (int index = 0; index < 10; index++) {
                    connections.add(pool.getConnection());
                }
                try {
                    TimeUnit.SECONDS.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int index = 0; index < 10; index++) {
                    try {
                        connections.get(index).close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        TimeUnit.SECONDS.sleep(3);
        pool.destroyConnections();
    }

    @Test (expectedExceptions = CloneNotSupportedException.class)
    public void checkDefenceFromClone() throws CloneNotSupportedException {
        ConnectionPool.getInstance().clone();
    }
}
