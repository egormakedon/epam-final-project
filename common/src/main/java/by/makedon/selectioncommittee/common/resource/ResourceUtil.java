package by.makedon.selectioncommittee.common.resource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author Yahor Makedon
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class ResourceUtil {
    private static final PropertyContext propertyContext = new PropertyContext();

    static {
        Path classPath = Paths.get(Objects.requireNonNull(
            Thread.currentThread().getContextClassLoader().getResource("")).getPath());
        try {
            FileRegistrar fileRegistrar = new PropertyFileRegistrar(propertyContext);
            FileRegistrationVisitor visitor = new FileRegistrationVisitor(fileRegistrar, FileRegistrar::register);

            log.debug("Begin property files registration");
            Files.walkFileTree(classPath, visitor);
            log.debug("End property files registration");
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }

    public static PropertyHolder resource(@NotNull String resourceName) {
        return propertyContext.getResource(resourceName);
    }
}
