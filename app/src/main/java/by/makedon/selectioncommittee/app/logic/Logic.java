package by.makedon.selectioncommittee.app.logic;

import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static by.makedon.selectioncommittee.app.configuration.util.ErrorMessageTemplate.INVALID_INPUT_PARAMETERS_SIZE_WITH_EXPECTED_ACTUAL;

public interface Logic {
    default void doAction(@NotNull List<String> parameters) throws LogicException {
        Objects.requireNonNull(parameters);

        try {
            validateParametersSize(parameters);
            validate(parameters);
            action(parameters);
        } catch (ValidationException | DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    default void validateParametersSize(@NotNull List<String> parameters) throws ValidationException {
        int validParametersSize = getValidParametersSize();
        if (parameters.size() != validParametersSize) {
            final String message = String.format(
                INVALID_INPUT_PARAMETERS_SIZE_WITH_EXPECTED_ACTUAL, validParametersSize, parameters.size());
            throw new ValidationException(message);
        }
    }

    int getValidParametersSize();
    void validate(@NotNull List<String> parameters) throws ValidationException;
    void action(@NotNull List<String> parameters) throws DaoException, LogicException;
}
