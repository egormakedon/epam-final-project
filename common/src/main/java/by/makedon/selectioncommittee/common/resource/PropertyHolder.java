package by.makedon.selectioncommittee.common.resource;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * @author Yahor Makedon
 */
@FunctionalInterface
public interface PropertyHolder {
    Optional<String> property(@NotNull String propertyKey);
}
