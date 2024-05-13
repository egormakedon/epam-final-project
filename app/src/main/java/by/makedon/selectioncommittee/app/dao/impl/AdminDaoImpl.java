package by.makedon.selectioncommittee.app.dao.impl;

import by.makedon.selectioncommittee.app.dao.AdminDao;
import by.makedon.selectioncommittee.app.entity.AdminEnrolleeForm;
import by.makedon.selectioncommittee.app.entity.EnrolleeState;
import by.makedon.selectioncommittee.app.entity.SpecialityState;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class AdminDaoImpl implements AdminDao {
    private static final Logger logger = LoggerFactory.getLogger(AdminDaoImpl.class);
    private static final String STATEMENT_IN_PROCESS = "В процессе";

    private static final AdminDao instance;
    private static final AtomicBoolean isInstanceCreated;

    static {
        isInstanceCreated = new AtomicBoolean(false);
        instance = new AdminDaoImpl();
        isInstanceCreated.set(true);
    }

    private AdminDaoImpl() {
        if (isInstanceCreated.get() || instance != null) {
            final String message = "AdminDaoImpl instance has been already created " +
                "or it is attempt to clone AdminDaoImpl using Reflection API";
            logger.error(message);
            throw new RuntimeException(message);
        }
    }

    public static AdminDao getInstance() {
        return instance;
    }

    @Override
    public Set<EnrolleeState> getEnrolleeStates() throws DaoException {
        Optional<Set<EnrolleeState>> resultOptional = executeReadOnlyMode(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ENROLLEE_ID_SPECIALITY_ID_SCORE);
                logger.debug("Successfully have selected all Enrollee States");

                Set<EnrolleeState> enrolleeStates = new HashSet<>();
                while (resultSet.next()) {
                    EnrolleeState enrolleeState = mapToEnrolleeState(resultSet);
                    enrolleeStates.add(enrolleeState);
                }

                return Optional.of(enrolleeStates);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        Set<EnrolleeState> result = resultOptional.orElse(Collections.emptySet());
        if (result.isEmpty()) {
            throw new DaoException("enrollee form is empty");
        }

        logger.info("Successfully has returned set of all EnrolleeState:\n{}", result);
        return result;
    }

    private static EnrolleeState mapToEnrolleeState(ResultSet resultSet) throws SQLException {
        short scoreValue = resultSet.getShort(FIELD_SCORE);
        long enrolleeIdValue = resultSet.getLong(FIELD_ENROLLEE_ID);
        long specialityIdValue = resultSet.getLong(FIELD_SPECIALITY_ID);
        String dateValue = resultSet.getString(FIELD_DATE);

        return new EnrolleeState(enrolleeIdValue, specialityIdValue, scoreValue, dateValue);
    }

    @Override
    public Map<Long, Integer> getSpecialityIdToNumberOfSeatsMap() throws DaoException {
        Optional<Map<Long, Integer>> resultOptional = executeReadOnlyMode(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_SPECIALITY_ID_NUMBER_OF_SEATS);
                logger.debug("Successfully have selected all specialityId and numberOfSeats");

                Map<Long, Integer> specialityIdToNumberOfSeatsMap = new HashMap<>();
                while (resultSet.next()) {
                    populateSpecialityIdToNumberOfSeatsMap(resultSet, specialityIdToNumberOfSeatsMap::put);
                }

                return Optional.of(specialityIdToNumberOfSeatsMap);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        Map<Long, Integer> result = resultOptional.orElse(Collections.emptyMap());
        if (result.isEmpty()) {
            throw new DaoException("enrollee form is empty");
        }

        logger.info("Successfully has returned specialityId to numberOfSeats map:\n{}", result);
        return result;
    }

    private static void populateSpecialityIdToNumberOfSeatsMap(
        ResultSet resultSet, BiConsumer<Long, Integer> mapPopulateConsumer) throws SQLException {

        long specialityIdValue = resultSet.getLong(FIELD_SPECIALITY_ID);
        int numberOfSeatsValue = resultSet.getInt(FIELD_NUMBER_OF_SEATS);

        mapPopulateConsumer.accept(specialityIdValue, numberOfSeatsValue);
    }

    @Override
    public void refreshStatement(@NotNull Set<EnrolleeState> enrolleeStates) throws DaoException {
        executeTransactionMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STATEMENT_BY_ENROLLEE_ID)) {
                for (EnrolleeState enrolleeState : enrolleeStates) {
                    appendEnrolleeStateToBatch(enrolleeState, preparedStatement);
                }
                preparedStatement.executeBatch();
                connection.commit();
                return Optional.empty();
            } catch (SQLException e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });
        logger.info("Successfully has updated set of EnrolleeState:\n{}", enrolleeStates);
    }

    private static void appendEnrolleeStateToBatch(
        EnrolleeState enrolleeState, PreparedStatement preparedStatement) throws SQLException {

        preparedStatement.setString(1, enrolleeState.getStatement());
        preparedStatement.setLong(2, enrolleeState.getEnrolleeId());
        preparedStatement.addBatch();
    }

    @Override
    public void resetStatement() throws DaoException {
        executeTransactionMode(connection -> {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(SQL_UPDATE_RESET_STATEMENT);
                connection.commit();
                return Optional.empty();
            } catch (SQLException e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });
        logger.info("Successfully have been reset all Enrollee Statements to default");
    }

    @Override
    public void deleteEnrolleeFormByUsername(@NotNull String usernameValue) throws DaoException {
        executeTransactionMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ENROLLEE_BY_USERNAME)) {
                preparedStatement.setString(1, usernameValue);
                preparedStatement.executeUpdate();
                connection.commit();
                return Optional.empty();
            } catch (SQLException e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });
        logger.info("Successfully has deleted Enrollee by provided username: {}", usernameValue);
    }

    @Override
    public void deleteUserByUsername(@NotNull String usernameValue) throws DaoException {
        Optional<Integer> result = executeTransactionMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_BY_USERNAME)) {
                preparedStatement.setString(1, usernameValue);
                int updatedRowsCount = preparedStatement.executeUpdate();
                connection.commit();
                return updatedRowsCount == 0 ? Optional.empty() : Optional.of(updatedRowsCount);
            } catch (SQLException e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });

        if (!result.isPresent()) {
            final String message = String.format("user [%s] does not exist", usernameValue);
            throw new DaoException(message);
        }

        logger.info("Successfully has deleted User by provided username: {}", usernameValue);
    }

    @Override
    public boolean isStatementInProcess() throws DaoException {
        Optional<String> result = executeReadOnlyMode(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_FIRST_STATEMENT);

                if (resultSet.next()) {
                    String statementValue = resultSet.getString(FIELD_STATEMENT);
                    return Optional.ofNullable(statementValue);
                }
                return Optional.empty();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        boolean isStatementInProcess = result
            .map(statementValue -> statementValue.equals(STATEMENT_IN_PROCESS))
            .orElse(Boolean.FALSE);
        logger.info("Successfully has returned status of statement is in process: {}", isStatementInProcess);
        return isStatementInProcess;
    }

    @Override
    public void updateNumberOfSeatsBy(@NotNull String specialityValue, int numberOfSeatsValue) throws DaoException {
        Optional<Integer> result = executeTransactionMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_NUMBER_OF_SEATS_BY_SPECIALITY)) {
                preparedStatement.setInt(1, numberOfSeatsValue);
                preparedStatement.setString(2, specialityValue);
                int updatedRowsCount = preparedStatement.executeUpdate();
                connection.commit();
                return updatedRowsCount == 0 ? Optional.empty() : Optional.of(updatedRowsCount);
            } catch (SQLException e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });

        if (!result.isPresent()) {
            final String message = String.format("speciality [%s] does not exist", specialityValue);
            throw new DaoException(message);
        }

        logger.info("Successfully has updated number of seats: `{}` for provided speciality: `{}`", numberOfSeatsValue, specialityValue);
    }

    @Override
    public List<AdminEnrolleeForm> getAdminEnrolleeFormList() throws DaoException {
        Optional<List<AdminEnrolleeForm>> resultOptional = executeReadOnlyMode(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ADMIN_ENROLLEE_FORMS);
                logger.debug("Successfully have selected all AdminEnrolleeForm");

                List<AdminEnrolleeForm> adminEnrolleeFormList = new ArrayList<>();
                while (resultSet.next()) {
                    populateAdminEnrolleeFormList(resultSet, adminEnrolleeFormList::add);
                }

                return Optional.of(adminEnrolleeFormList);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        List<AdminEnrolleeForm> result = resultOptional.orElse(Collections.emptyList());
        if (result.isEmpty()) {
            throw new DaoException("enrollee form is empty");
        }

        logger.info("Successfully has returned AdminEnrolleeForm list:\n{}", result);
        return result;
    }

    private static void populateAdminEnrolleeFormList(
        ResultSet resultSet, Consumer<AdminEnrolleeForm> listPopulateConsumer) throws SQLException {

        AdminEnrolleeForm adminEnrolleeForm = AdminEnrolleeForm.builder()
            .username(resultSet.getString(FIELD_USERNAME))
            .passportId(resultSet.getString(FIELD_PASSPORT_ID))
            .countryDomen(resultSet.getString(FIELD_COUNTRY_DOMEN))
            .surname(resultSet.getString(FIELD_SURNAME))
            .name(resultSet.getString(FIELD_NAME))
            .secondName(resultSet.getString(FIELD_SECOND_NAME))
            .phone(resultSet.getString(FIELD_PHONE))
            .speciality(resultSet.getString(FIELD_SPECIALITY))
            .score(resultSet.getInt(FIELD_SCORE))
            .build();
        listPopulateConsumer.accept(adminEnrolleeForm);
    }

    @Override
    public List<SpecialityState> getSpecialityStateList() throws DaoException {
        Optional<List<SpecialityState>> resultOptional = executeReadOnlyMode(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_SPECIALITY_STATES);
                logger.debug("Successfully have selected all SpecialityState");

                List<SpecialityState> specialityStateList = new ArrayList<>();
                while (resultSet.next()) {
                    populateSpecialityStateList(resultSet, specialityStateList::add);
                }

                return Optional.of(specialityStateList);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        List<SpecialityState> result = resultOptional.orElse(Collections.emptyList());
        if (result.isEmpty()) {
            throw new DaoException("no one speciality does exist");
        }

        logger.info("Successfully has returned SpecialityState list:\n{}", result);
        return result;
    }

    private static void populateSpecialityStateList(
        ResultSet resultSet, Consumer<SpecialityState> listPopulateConsumer) throws SQLException {

        SpecialityState specialityState = SpecialityState.builder()
            .speciality(resultSet.getString(FIELD_SPECIALITY))
            .numberOfSeats(resultSet.getInt(FIELD_NUMBER_OF_SEATS))
            .filledDocuments(resultSet.getInt(FIELD_FILLED_DOCUMENTS))
            .build();
        listPopulateConsumer.accept(specialityState);
    }

    @Override
    public List<String> getUserEmailList() throws DaoException {
        Optional<List<String>> resultOptional = executeReadOnlyMode(connection -> {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_EMAIL_IS_NULL_E_ID);
                logger.debug("Successfully have selected all User emails when Enrollee is null");

                List<String> emailList = new ArrayList<>();
                while (resultSet.next()) {
                    int result = resultSet.getInt(FIELD_RESULT);
                    if (result == 0) {
                        emailList.add(resultSet.getString(FIELD_EMAIl));
                    }
                }
                return Optional.of(emailList);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        List<String> result = resultOptional.orElse(Collections.emptyList());
        logger.info("Successfully has returned User email list:\n{}", result);
        return result;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public AdminDaoImpl clone() throws CloneNotSupportedException {
        final String message = "Prohibited attempt to clone AdminDaoImpl";
        logger.error(message);
        throw new CloneNotSupportedException(message);
    }
}
