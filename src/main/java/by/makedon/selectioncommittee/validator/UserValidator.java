package by.makedon.selectioncommittee.validator;

import com.sun.istack.internal.NotNull;

import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMAIL_REGEXP = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)$";
    private static final String USERNAME_REGEXP = "^[a-zA-Z][a-zA-Z0-9-_\\.]{3,15}$";
    private static final String PASSWORD_REGEXP = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}";

    public static boolean validateEmail(@NotNull String emailValue) {
        return emailValue != null && Pattern.matches(EMAIL_REGEXP, emailValue);
    }
    public static boolean validateUsername(@NotNull String usernameValue) {
        return usernameValue != null && Pattern.matches(USERNAME_REGEXP, usernameValue);
    }
    public static boolean validatePassword(@NotNull String passwordValue) {
        return passwordValue != null && Pattern.matches(PASSWORD_REGEXP, passwordValue);
    }
    public static boolean arePasswordsEqual(@NotNull String password1Value, @NotNull String password2Value) {
        return password1Value != null && password2Value != null && password1Value.equals(password2Value);
    }
}