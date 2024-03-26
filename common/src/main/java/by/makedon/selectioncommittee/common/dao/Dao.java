package by.makedon.selectioncommittee.common.dao;

import by.makedon.selectioncommittee.common.connectionpool.ConnectionPool;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Yahor Makedon
 */
public interface Dao<T, ID> {
    String GENERATED_KEY = "GENERATED_KEY";

    default void create(T entity) {
        final String message = String.format("`%s` does not support operation `create`!", this.getClass().getSimpleName());
        throw new UnsupportedOperationException(message);
    }
    default Optional<T> read(ID id) {
        final String message = String.format("`%s` does not support operation `read`!", this.getClass().getSimpleName());
        throw new UnsupportedOperationException(message);
    }
    default List<T> readAll() {
        final String message = String.format("`%s` does not support operation `readAll`!", this.getClass().getSimpleName());
        throw new UnsupportedOperationException(message);
    }
    default void update(T entity) {
        final String message = String.format("`%s` does not support operation `update`!", this.getClass().getSimpleName());
        throw new UnsupportedOperationException(message);
    }
    default void delete(T entity) {
        final String message = String.format("`%s` does not support operation `delete`!", this.getClass().getSimpleName());
        throw new UnsupportedOperationException(message);
    }

    default <R> Optional<R> executeTransactionMode(int transactionIsolationLevel,
                                                   Function<Connection, Optional<R>> executeFunction) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setReadOnly(false);
            connection.setTransactionIsolation(transactionIsolationLevel);
            connection.setAutoCommit(false);
            getLogger().debug("executeTransactionMode(): readOnly=`false` " +
                "transactionIsolationLevel=`{}`, autoCommit=`false`", transactionIsolationLevel);
            return executeFunction.apply(connection);
        } catch (SQLException e) {
            getLogger().warn(e.getMessage(), e);
            return Optional.empty();
        }
    }

    default void rollback(Connection connection) {
        try {
            getLogger().debug("Processing rollback within transaction!!");
            connection.rollback();
            getLogger().debug("Changes have been successfully rolled back!!");
        } catch (SQLException e) {
            getLogger().warn(e.getMessage(), e);
        }
    }

    default <R> Optional<R> executeDefaultMode(Function<Connection, Optional<R>> executeFunction) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setReadOnly(false);
            connection.setAutoCommit(true);
            getLogger().debug("executeDefaultMode(): readOnly=`false` autoCommit=`true`");
            return executeFunction.apply(connection);
        } catch (SQLException e) {
            getLogger().warn(e.getMessage(), e);
            return Optional.empty();
        }
    }

    default <R> Optional<R> executeReadOnlyMode(Function<Connection, Optional<R>> executeFunction) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setReadOnly(true);
            connection.setAutoCommit(true);
            getLogger().debug("executeReadOnlyMode(): readOnly=`true` autoCommit=`true`");
            return executeFunction.apply(connection);
        } catch (SQLException e) {
            getLogger().warn(e.getMessage(), e);
            return Optional.empty();
        }
    }

    Logger getLogger();
}
