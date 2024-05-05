package by.makedon.selectioncommittee.app.validator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

public final class NumberOfSeatsValidator {
    private static final String NUMBER_REGEXP = "^([0-9]+)$";

    public boolean validate(@NotNull String numberOfSeatsValue) {
        Objects.requireNonNull(numberOfSeatsValue);
        return Pattern.matches(NUMBER_REGEXP, numberOfSeatsValue);
    }
}
