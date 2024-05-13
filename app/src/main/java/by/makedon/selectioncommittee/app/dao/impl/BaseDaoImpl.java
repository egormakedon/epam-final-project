package by.makedon.selectioncommittee.app.dao.impl;

import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.entity.User;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.USER_NOT_EXIST_WITH_EMAIL;
import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.USER_NOT_EXIST_WITH_USERNAME;

public final class BaseDaoImpl implements BaseDao {
    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    private static final BaseDao instance;
    private static final AtomicBoolean isInstanceCreated;

    static {
        isInstanceCreated = new AtomicBoolean(false);
        instance = new BaseDaoImpl();
        isInstanceCreated.set(true);
    }

    private BaseDaoImpl() {
        if (isInstanceCreated.get() || instance != null) {
            final String message = "BaseDaoImpl instance has been already created " +
                "or it is attempt to clone BaseDaoImpl using Reflection API";
            logger.error(message);
            throw new RuntimeException(message);
        }
    }

    public static BaseDao getInstance() {
        return instance;
    }

    @Override
    public void insertUser(@NotNull User user) throws DaoException {
        executeTransactionMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
                appendUserToCreationStatement(preparedStatement, user);
                preparedStatement.executeUpdate();
                connection.commit();
                return Optional.empty();
            } catch (SQLException e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });
        logger.info("Successfully has inserted User:\n{}", user);
    }

    private static void appendUserToCreationStatement(
        PreparedStatement preparedStatement, User user) throws SQLException {

        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());
    }

    @Override
    public boolean matchUsernameAndPassword(@NotNull String usernameValue, @NotNull String passwordValue) throws DaoException {
        Optional<Boolean> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ID_BY_USERNAME_PASSWORD)) {
                preparedStatement.setString(1, usernameValue);
                preparedStatement.setString(2, passwordValue);
                ResultSet resultSet = preparedStatement.executeQuery();
                return Optional.of(resultSet.next());
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        boolean result = resultOptional.orElse(Boolean.FALSE);
        logger.info("Do Username: `{}` and Password: `*******` match: {}", usernameValue, result);
        return result;
    }

    @Override
    public boolean matchEmailAndUsername(@NotNull String emailValue, @NotNull String usernameValue) throws DaoException {
        Optional<Boolean> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ID_BY_EMAIL_USERNAME)) {
                preparedStatement.setString(1, emailValue);
                preparedStatement.setString(2, usernameValue);
                ResultSet resultSet = preparedStatement.executeQuery();
                return Optional.of(resultSet.next());
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        boolean result = resultOptional.orElse(Boolean.FALSE);
        logger.info("Do Email: `{}` and Username: `{}` match: {}", emailValue, usernameValue, result);
        return result;
    }

    @Override
    public String getTypeByUsername(@NotNull String usernameValue) throws DaoException {
        Optional<String> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TYPE_BY_USERNAME)) {
                preparedStatement.setString(1, usernameValue);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String typeValue = resultSet.getString(FIELD_TYPE);
                    return Optional.ofNullable(typeValue);
                }
                return Optional.empty();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        String result = resultOptional
            .orElseThrow(() -> new DaoException(String.format(USER_NOT_EXIST_WITH_USERNAME, usernameValue)));
        logger.info("Successfully has returned Type: `{}` for provided username: `{}`", result, usernameValue);
        return result;
    }

    @Override
    public String getUsernameByEmail(@NotNull String emailValue) throws DaoException {
        Optional<String> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERNAME_BY_EMAIL)) {
                preparedStatement.setString(1, emailValue);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String usernameValue = resultSet.getString(FIELD_USERNAME);
                    return Optional.ofNullable(usernameValue);
                }
                return Optional.empty();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        String result = resultOptional
            .orElseThrow(() -> new DaoException(String.format(USER_NOT_EXIST_WITH_EMAIL, emailValue)));
        logger.info("Successfully has returned Username: `{}` for provided Email: `{}`", result, emailValue);
        return result;
    }

    @Override
    public void updatePasswordByUsername(@NotNull String usernameValue, @NotNull String newPasswordValue) throws DaoException {
        Optional<Integer> result = executeTransactionMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
                preparedStatement.setString(1, newPasswordValue);
                preparedStatement.setString(2, usernameValue);
                int updatedRowsCount = preparedStatement.executeUpdate();
                connection.commit();
                return updatedRowsCount == 0 ? Optional.empty() : Optional.of(updatedRowsCount);
            } catch (SQLException e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });

        if (!result.isPresent()) {
            final String message = String.format(USER_NOT_EXIST_WITH_USERNAME, usernameValue);
            throw new DaoException(message);
        }

        logger.info("Successfully has updated Password for provided User: {}", usernameValue);
    }

    @Override
    public boolean matchUsername(@NotNull String usernameValue) throws DaoException {
        Optional<Boolean> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ID_BY_USERNAME)) {
                preparedStatement.setString(1, usernameValue);
                ResultSet resultSet = preparedStatement.executeQuery();
                return Optional.of(resultSet.next());
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        boolean result = resultOptional.orElse(Boolean.FALSE);
        logger.info("Does Username: `{}` match: {}", usernameValue, result);
        return result;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public BaseDaoImpl clone() throws CloneNotSupportedException {
        final String message = "Prohibited attempt to clone BaseDaoImpl";
        logger.error(message);
        throw new CloneNotSupportedException(message);
    }
}
