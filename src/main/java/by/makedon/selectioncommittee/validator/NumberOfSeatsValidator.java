package by.makedon.selectioncommittee.validator;

import com.sun.istack.internal.NotNull;

import java.util.regex.Pattern;

public class NumberOfSeatsValidator {
    private static final String NUMBER_REGEXP = "[0-9]+";

    public static boolean validate(@NotNull String numberOfSeatsValue) {
        return numberOfSeatsValue != null && Pattern.matches(NUMBER_REGEXP, numberOfSeatsValue);
    }
}