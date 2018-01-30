package by.makedon.finalproject.validator;

import java.util.regex.Pattern;

public class NumberOfSeatsValidator {
    private static final String NUMBER_REGEXP = "[0-9]+";

    public static boolean validate(String numberOfSeatsValue) {
        return Pattern.matches(NUMBER_REGEXP, numberOfSeatsValue);
    }
}
