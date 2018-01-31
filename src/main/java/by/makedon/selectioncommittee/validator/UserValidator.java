package by.makedon.selectioncommittee.validator;

import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMAIL_REGEXP = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)$";
    private static final String USERNAME_REGEXP = "^[a-zA-Z][a-zA-Z0-9-_\\.]{3,15}$";
    private static final String PASSWORD_REGEXP = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}";

    public static boolean validateEmail(String emailValue) {
        return Pattern.matches(EMAIL_REGEXP, emailValue);
    }

    public static boolean validateUsername(String usernameValue) {
        return Pattern.matches(USERNAME_REGEXP, usernameValue);
    }

    public static boolean validatePassword(String passwordValue) {
        return Pattern.matches(PASSWORD_REGEXP, passwordValue);
    }

    public static boolean arePasswordsEqual(String password1Value, String password2Value) {
        return password1Value.equals(password2Value);
    }
}