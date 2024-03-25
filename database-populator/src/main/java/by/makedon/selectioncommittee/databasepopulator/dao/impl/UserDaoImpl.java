package by.makedon.selectioncommittee.databasepopulator.dao.impl;

import by.makedon.selectioncommittee.common.dao.DaoException;
import by.makedon.selectioncommittee.databasepopulator.dao.UserDao;
import by.makedon.selectioncommittee.databasepopulator.entity.User;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Yahor Makedon
 */
@Slf4j
public class UserDaoImpl implements UserDao {
    @Override
    public void batchCreate(List<User> users) {
        executeTransactionMode(Connection.TRANSACTION_READ_COMMITTED, connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
                for (List<User> usersBatch : Lists.partition(users, BATCH_SIZE)) {
                    for (User user : usersBatch) {
                        appendUserToCreationBatch(preparedStatement, user);
                    }
                    preparedStatement.executeBatch();
                    log.debug("Successfully has inserted users batch:\n{}", usersBatch);
                }
                connection.commit();
                return Optional.empty();
            } catch (SQLException e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });
        log.info("Successfully has inserted list of users:\n{}", users);
    }

    private static void appendUserToCreationBatch(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setInt(4, user.getEnrollee().getEnrolleeId());
        preparedStatement.addBatch();
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
