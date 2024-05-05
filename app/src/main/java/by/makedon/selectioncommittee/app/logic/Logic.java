package by.makedon.selectioncommittee.app.logic;

import by.makedon.selectioncommittee.app.validator.ValidationException;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public interface Logic {
    default void doAction(@NotNull List<String> parameters) throws LogicException {
        Objects.requireNonNull(parameters);

        try {
            validate(parameters);
            action(parameters);
        } catch (ValidationException | DaoException e) {
            throw new LogicException(e.getMessage(), e);
        }
    }

    void validate(@NotNull List<String> parameters) throws ValidationException;
    void action(@NotNull List<String> parameters) throws DaoException, LogicException;
}
