package by.makedon.selectioncommittee.common.resource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yahor Makedon
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class PropertyContext {
    private static final ReentrantLock loadResourceLock = new ReentrantLock();

    private final Map<String, Path> files = new HashMap<>();
    private final Map<String, PropertyHolder> resources = new HashMap<>();

    @NotNull
    public PropertyHolder getResource(@NotNull String resourceName) {
        return takeResourceBy(resourceName)
            .orElse(validateAndLoadResource(resourceName));
    }

    private Optional<PropertyHolder> takeResourceBy(@NotNull String resourceName) {
        return Optional.ofNullable(resources.get(resourceName));
    }
    private Optional<Path> takeFileBy(@NotNull String fileName) {
        return Optional.ofNullable(files.get(fileName));
    }

    @NotNull
    private PropertyHolder validateAndLoadResource(@NotNull String resourceName) {
        loadResourceLock.lock();
        log.debug("PropertyContext: load resource lock!");
        try {
            validateResourceMissing(resourceName);
            return takeResourceBy(resourceName)
                .orElse(loadResource(resourceName));
        } finally {
            loadResourceLock.unlock();
            log.debug("PropertyContext: load resource unlock!");
        }
    }

    private void validateResourceMissing(@NotNull String resourceName) {
        if (isResourceNotExisted(resourceName)) {
            final String message = String.format("Requested resource `%s` does not exist", resourceName);
            log.error(message);
            throw new ResourceMissingException(message);
        }
    }
    private boolean isResourceNotExisted(@NotNull String resourceName) {
        return !isResourceExisted(resourceName);
    }
    private boolean isResourceExisted(@NotNull String resourceName) {
        return takeResourceBy(resourceName).isPresent()
            || takeFileBy(resourceName).isPresent();
    }

    @NotNull
    @SneakyThrows
    private PropertyHolder loadResource(@NotNull String resourceName) {
        log.debug("Trying to load resource `{}`...", resourceName);

        Properties properties = new Properties();
        properties.load(Files.newInputStream(files.get(resourceName)));
        PropertyHolder resource = propertyKey -> Optional.ofNullable(properties.getProperty(propertyKey));

        resources.put(resourceName, resource);
        files.remove(resourceName);

        log.debug("Resource `{}` is loaded successfully", resourceName);
        return resource;
    }

    public void putFile(@NotNull String fileName, @NotNull Path file) {
        files.put(fileName, file);
    }

    public boolean containsFile(@Nullable String fileName) {
        return files.containsKey(fileName);
    }
}
