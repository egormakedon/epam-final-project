package by.makedon.selectioncommittee.app.validator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

public final class UserValidator {
    private static final String EMAIL_REGEXP = "^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)$";
    private static final String USERNAME_REGEXP = "^[a-zA-Z][a-zA-Z0-9-_\\.]{3,15}$";
    private static final String PASSWORD_REGEXP = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}";

    public boolean validateEmail(@NotNull String emailValue) {
        Objects.requireNonNull(emailValue);
        return Pattern.matches(EMAIL_REGEXP, emailValue);
    }

    public boolean validateUsername(@NotNull String usernameValue) {
        Objects.requireNonNull(usernameValue);
        return Pattern.matches(USERNAME_REGEXP, usernameValue);
    }

    public boolean validatePassword(@NotNull String passwordValue) {
        Objects.requireNonNull(passwordValue);
        return Pattern.matches(PASSWORD_REGEXP, passwordValue);
    }

    public boolean arePasswordsEqual(@NotNull String password1Value, @NotNull String password2Value) {
        Objects.requireNonNull(password1Value);
        Objects.requireNonNull(password2Value);
        return password1Value.equals(password2Value);
    }
}
