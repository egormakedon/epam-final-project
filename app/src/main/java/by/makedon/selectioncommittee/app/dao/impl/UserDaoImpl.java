package by.makedon.selectioncommittee.app.dao.impl;

import by.makedon.selectioncommittee.app.dao.UserDao;
import by.makedon.selectioncommittee.app.entity.enrolleeform.EnrolleeForm;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public final class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private static final String STATEMENT_IN_PROCESS = "В процессе";

    private static final UserDao instance;
    private static final AtomicBoolean isInstanceCreated;

    static {
        isInstanceCreated = new AtomicBoolean(false);
        instance = new UserDaoImpl();
        isInstanceCreated.set(true);
    }

    private UserDaoImpl() {
        if (isInstanceCreated.get() || instance != null) {
            final String message = "UserDaoImpl instance has been already created " +
                "or it is attempt to clone UserDaoImpl using Reflection API";
            logger.error(message);
            throw new RuntimeException(message);
        }
    }

    public static UserDao getInstance() {
        return instance;
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
    public boolean isUserEnrolleeFormInserted(@NotNull String usernameValue) throws DaoException {
        Optional<Boolean> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_IS_NULL_E_ID_BY_USERNAME)) {
                preparedStatement.setString(1, usernameValue);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int result = resultSet.getInt(FIELD_RESULT);
                    return Optional.of(result == 0);
                }
                return Optional.empty();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        boolean result = resultOptional.orElse(Boolean.FALSE);
        logger.info("Does User with Username: `{}` exist: {}", usernameValue, result);
        return result;
    }

    @Override
    public void saveEnrolleeFormByUsername(@NotNull String usernameValue, @NotNull EnrolleeForm enrolleeForm) throws DaoException {
        Optional<Long> enrolleeIdOptional = executeTransactionMode(connection -> {
            try {
                long enrolleeId = saveEnrolleeForm(connection, enrolleeForm);
                updateUserWithEnrolleeId(connection, usernameValue, enrolleeId);
                return Optional.of(enrolleeId);
            } catch (Exception e) {
                rollback(connection);
                throw new DaoException(e.getMessage(), e);
            }
        });

        Long enrolleeId = enrolleeIdOptional.orElse(null);
        logger.info("Successfully has saved EnrolleeForm:\nenrolleeId: `{}`, enrolleeForm: `{}`\nfor provided Username: `{}`", enrolleeId, enrolleeForm, usernameValue);
    }

    private static long saveEnrolleeForm(@NotNull Connection connection, @NotNull EnrolleeForm enrolleeForm) throws DaoException, SQLException {
        long specialityId = getSpecialityIdByEnrolleeForm(connection, enrolleeForm);

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ENROLLEE_FORM, new String[]{ AUTO_GENERATED_KEY_ENROLLEE_ID })) {
            populateStatementWithEnrolleeFormAndSpecialityId(preparedStatement, enrolleeForm, specialityId);
            preparedStatement.executeUpdate();
            logger.debug("Successfully has executed EnrolleeForm insertion");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(GENERATED_KEY);
            }
            throw new DaoException("enrollee form has not been inserted");
        }
    }

    private static long getSpecialityIdByEnrolleeForm(Connection connection, EnrolleeForm enrolleeForm) throws DaoException, SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_S_ID_BY_UNIVERSITY_FACULTY_SPECIALITY)) {
            preparedStatement.setString(1, enrolleeForm.getUniversity());
            preparedStatement.setString(2, enrolleeForm.getFaculty());
            preparedStatement.setString(3, enrolleeForm.getSpeciality());
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug("Successfully has executed query of returning specialityId by university: `{}`, faculty: `{}`, speciality: `{}`",
                enrolleeForm.getUniversity(), enrolleeForm.getFaculty(), enrolleeForm.getSpeciality());

            if (resultSet.next()) {
                return resultSet.getLong(FIELD_SPECIALITY_ID);
            }
            throw new DaoException("speciality does not exist");
        }
    }

    private static void populateStatementWithEnrolleeFormAndSpecialityId(PreparedStatement preparedStatement, EnrolleeForm enrolleeForm, long specialityId) throws SQLException {
        preparedStatement.setString(1, enrolleeForm.getPassportId());
        preparedStatement.setString(2, enrolleeForm.getCountryDomen());
        preparedStatement.setLong(3, specialityId);
        preparedStatement.setString(4, enrolleeForm.getSurname());
        preparedStatement.setString(5, enrolleeForm.getName());
        preparedStatement.setString(6, enrolleeForm.getSecondName());
        preparedStatement.setString(7, enrolleeForm.getPhone());
        preparedStatement.setByte(8, enrolleeForm.getRussianLang());
        preparedStatement.setByte(9, enrolleeForm.getBelorussianLang());
        preparedStatement.setByte(10, enrolleeForm.getPhysics());
        preparedStatement.setByte(11, enrolleeForm.getMath());
        preparedStatement.setByte(12, enrolleeForm.getChemistry());
        preparedStatement.setByte(13, enrolleeForm.getBiology());
        preparedStatement.setByte(14, enrolleeForm.getForeignLang());
        preparedStatement.setByte(15, enrolleeForm.getHistoryOfBelarus());
        preparedStatement.setByte(16, enrolleeForm.getSocialStudies());
        preparedStatement.setByte(17, enrolleeForm.getGeography());
        preparedStatement.setByte(18, enrolleeForm.getHistory());
        preparedStatement.setByte(19, enrolleeForm.getCertificate());
        preparedStatement.setString(20, enrolleeForm.getDate());
    }

    private static void updateUserWithEnrolleeId(@NotNull Connection connection, @NotNull String usernameValue, long enrolleeId) throws DaoException, SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_E_ID_IN_USER)) {
            preparedStatement.setLong(1, enrolleeId);
            preparedStatement.setString(2, usernameValue);
            int updatedRowsCount = preparedStatement.executeUpdate();
            logger.debug("Successfully has been updated User with provided enrolleeId");

            if (updatedRowsCount == 0) {
                throw new DaoException(String.format("user with username: `%s` does not exist", usernameValue));
            }
        }
    }

    @Override
    public void resetEnrolleeFormByUsername(@NotNull String usernameValue) throws DaoException {
        Optional<Integer> result = executeTransactionMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_RESET_ENROLLEE_FORM_BY_USERNAME)) {
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
            final String message = String.format("EnrolleeForm by username: `%s` does not exist", usernameValue);
            throw new DaoException(message);
        }

        logger.info("Successfully has deleted EnrolleeForm for provided User: {}", usernameValue);
    }

    @Override
    public String getEmailByUsername(@NotNull String usernameValue) throws DaoException {
        Optional<String> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_EMAIL_BY_USERNAME)) {
                preparedStatement.setString(1, usernameValue);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String emailValue = resultSet.getString(FIELD_EMAIL);
                    return Optional.ofNullable(emailValue);
                }
                return Optional.empty();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        String result = resultOptional
            .orElseThrow(() -> new DaoException(String.format("user with username: `%s` does not exist", usernameValue)));
        logger.info("Successfully has returned Email: `{}` for provided Username: `{}`", result, usernameValue);
        return result;
    }

    @Override
    public void updateEmailByUsername(@NotNull String usernameValue, @NotNull String newEmailValue) throws DaoException {
        Optional<Integer> result = executeTransactionMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CHANGE_EMAIL_BY_USERNAME)) {
                preparedStatement.setString(1, newEmailValue);
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
            final String message = String.format("user `%s` does not exist", usernameValue);
            throw new DaoException(message);
        }

        logger.info("Successfully has updated Email: `{}` for provided User: {}", newEmailValue, usernameValue);
    }

    @Override
    public void updateUsername(@NotNull String usernameValue, @NotNull String newUsernameValue) throws DaoException {
        Optional<Integer> result = executeTransactionMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CHANGE_USERNAME)) {
                preparedStatement.setString(1, newUsernameValue);
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
            final String message = String.format("user `%s` does not exist", usernameValue);
            throw new DaoException(message);
        }

        logger.info("Successfully has updated old Username: `{}` on the new provided Username: {}", usernameValue, newUsernameValue);
    }

    @Override
    public EnrolleeForm getEnrolleeFormByUsername(@NotNull String usernameValue) throws DaoException {
        Optional<EnrolleeForm> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ENROLLEE_FORM_BY_USERNAME)) {
                preparedStatement.setString(1, usernameValue);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    EnrolleeForm enrolleeForm = mapToEnrolleeForm(resultSet);
                    return Optional.of(enrolleeForm);
                }
                return Optional.empty();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        EnrolleeForm result = resultOptional
            .orElseThrow(() -> new DaoException(String.format("enrollee form has not found by provided username: `%s`", usernameValue)));
        logger.info("Successfully has returned EnrolleeForm:\n`{}`\nfor provided Username: `{}`", result, usernameValue);
        return result;
    }

    private static EnrolleeForm mapToEnrolleeForm(ResultSet resultSet) throws SQLException {
        return EnrolleeForm
            .builder()
            .university(resultSet.getString(FIELD_UNIVERSITY))
            .faculty(resultSet.getString(FIELD_FACULTY))
            .speciality(resultSet.getString(FIELD_SPECIALITY))
            .passportId(resultSet.getString(FIELD_PASSPORT_ID))
            .countryDomen(resultSet.getString(FIELD_COUNTRY_DOMEN))
            .surname(resultSet.getString(FIELD_SURNAME))
            .name(resultSet.getString(FIELD_NAME))
            .secondName(resultSet.getString(FIELD_SECOND_NAME))
            .phone(resultSet.getString(FIELD_PHONE))
            .russianLang(resultSet.getByte(FIELD_RUSSIAN_LANG))
            .belorussianLang(resultSet.getByte(FIELD_BELORUSSIAN_LANG))
            .physics(resultSet.getByte(FIELD_PHYSICS))
            .math(resultSet.getByte(FIELD_MATH))
            .chemistry(resultSet.getByte(FIELD_CHEMISTRY))
            .biology(resultSet.getByte(FIELD_BIOLOGY))
            .foreignLang(resultSet.getByte(FIELD_FOREIGN_LANG))
            .historyOfBelarus(resultSet.getByte(FIELD_HISTORY_OF_BELARUS))
            .socialStudies(resultSet.getByte(FIELD_SOCIAL_STUDIES))
            .geography(resultSet.getByte(FIELD_GEOGRAPHY))
            .history(resultSet.getByte(FIELD_HISTORY))
            .certificate(resultSet.getByte(FIELD_CERTIFICATE))
            .date(resultSet.getString(FIELD_DATE))
            .build();
    }

    @Override
    public String getStatementByUsername(@NotNull String usernameValue) throws DaoException {
        Optional<String> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_STATEMENT_BY_USERNAME)) {
                preparedStatement.setString(1, usernameValue);
                ResultSet resultSet = preparedStatement.executeQuery();

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

        String result = resultOptional
            .orElseThrow(() -> new DaoException(String.format("enrollee form has not found by provided username: `%s`", usernameValue)));
        logger.info("Successfully has returned Statement: `{}` for provided Username: `{}`", result, usernameValue);
        return result;
    }

    @Override
    public long getSpecialityIdByUsername(@NotNull String usernameValue) throws DaoException {
        Optional<Long> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_SPECIALITY_ID_BY_USERNAME)) {
                preparedStatement.setString(1, usernameValue);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    long specialityIdValue = resultSet.getLong(FIELD_SPECIALITY_ID);
                    return Optional.of(specialityIdValue);
                }
                return Optional.empty();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        long result = resultOptional
            .orElseThrow(() -> new DaoException(String.format("enrollee form has not found by provided username: `%s`", usernameValue)));
        logger.info("Successfully has returned specialityId: `{}` for provided Username: `{}`", result, usernameValue);
        return result;
    }

    @Override
    public int getEnrolleePlaceByUsernameAndSpecialityId(@NotNull String usernameValue, long specialityIdValue) throws DaoException {
        Optional<Integer> resultOptional = executeReadOnlyMode(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERNAME_ORDER_BY_SUBJECTS_SCORE_DESC)) {
                preparedStatement.setLong(1, specialityIdValue);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    if (usernameValue.equals(resultSet.getString(FIELD_USERNAME))) {
                        return Optional.of(resultSet.getRow());
                    }
                }
                return Optional.empty();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                return Optional.empty();
            }
        });

        int result = resultOptional
            .orElseThrow(() -> new DaoException(String.format("enrollee form has not found by provided username: `%s` and specialityId: `%s`", usernameValue, specialityIdValue)));
        logger.info("Successfully has returned Enrollee place: `{}` for provided Username: `{}` and Speciality ID: `{}`", result, usernameValue, specialityIdValue);
        return result;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public UserDaoImpl clone() throws CloneNotSupportedException {
        final String message = "Prohibited attempt to clone UserDaoImpl";
        logger.error(message);
        throw new CloneNotSupportedException(message);
    }
}
