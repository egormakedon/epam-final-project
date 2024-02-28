package by.makedon.selectioncommittee.common.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Yahor Makedon
 */
class ProxyConnection extends AbstractProxyConnection implements Connection {
    ProxyConnection(Connection connection) {
        super(connection);
    }

    @Override
    public void close() throws SQLException {
        ConnectionPool.getInstance().releaseConnection(this);
    }

    void closeConnection() throws SQLException {
        if (Objects.nonNull(connection)) {
            connection.close();
        }
    }
}
