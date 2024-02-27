package by.makedon.selectioncommittee.common.resource;

import com.google.common.io.Files;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Yahor Makedon
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class PropertyFileRegistrar implements FileRegistrar {
    public static final String PROPERTIES_EXTENSION = ".properties";
    private final PropertyContext propertyContext;

    @Override
    public void register(Path file) {
        if (isNotPropertyFile(file)) {
            return;
        }
        registerPropertyFile(file);
    }

    private static boolean isNotPropertyFile(@Nullable Path file) {
        return !isPropertyFile(file);
    }

    private static boolean isPropertyFile(@Nullable Path file) {
        return Objects.nonNull(file) && file.toString().endsWith(PROPERTIES_EXTENSION);
    }

    private void registerPropertyFile(@NotNull Path file) {
        String fileNameWithoutExtension = Files.getNameWithoutExtension(file.getFileName().toString());
        log.debug("Trying to register property file: {}", fileNameWithoutExtension);
        validate(fileNameWithoutExtension);
        propertyContext.putFile(fileNameWithoutExtension, file);
        log.debug("Property file `{}` has been registered successfully", fileNameWithoutExtension);
    }

    private void validate(@NotNull String fileName) {
        if (propertyContext.containsFile(fileName)) {
            final String message = String.format("Property file `%s` has been already registered", fileName);
            log.error(message);
            throw new FileRegistrationException(message);
        }
    }
}
