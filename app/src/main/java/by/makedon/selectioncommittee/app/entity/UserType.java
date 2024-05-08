package by.makedon.selectioncommittee.app.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Yahor Makedon
 */
public enum UserType {
    ADMIN, USER;

    public static UserType of(@NotNull String type) {
        Objects.requireNonNull(type);
        return UserType.valueOf(type.trim().toUpperCase());
    }

    public boolean isNotAdmin() {
        return !isAdmin();
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public boolean isNotUser() {
        return !isUser();
    }

    public boolean isUser() {
        return this == USER;
    }
}
