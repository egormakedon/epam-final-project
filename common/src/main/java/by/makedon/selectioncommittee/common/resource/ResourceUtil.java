package by.makedon.selectioncommittee.common.resource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Yahor Makedon
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class ResourceUtil {
    public static final String DEFAULT_RESOURCE_ROOT_FOLDER = "props";
    private static final PropertyContext propertyContext = new PropertyContext();

    static {
        URL classPathUrl = getClassPathUrl();
        Path classPath = Paths.get(classPathUrl.getPath());
        log.debug("Root class path for files registration: {}", classPath);
        registerFilesFrom(classPath);
    }

    private static URL getClassPathUrl() {
        return getDefaultClassPathUrl().orElseGet(
            () -> getJarClassPathUrl().orElseThrow(ResourcesLocationNotFoundException::new));
    }

    private static Optional<URL> getDefaultClassPathUrl() {
        log.debug("Trying to get default class path url...");
        Optional<URL> result = Optional.ofNullable(
            Thread.currentThread().getContextClassLoader().getResource(""));
        log.debug(result.isPresent() ? "Default class path url is found!!" : "Default class path url is not found!!");
        return result;
    }

    private static Optional<URL> getJarClassPathUrl() {
        log.debug("Trying to get jar class path url...");
        Optional<URL> result = Optional.ofNullable(
            ResourceUtil.class.getProtectionDomain().getCodeSource()).map(CodeSource::getLocation);
        log.debug(result.isPresent() ? "Jar class path url is found!!" : "Jar class path url is not found!!");
        return result;
    }

    private static void registerFilesFrom(Path classPath) {
        final String jarExtension = ".jar";
        if (classPath.toString().endsWith(jarExtension)) {
            runFilesRegistrationWithinJar(classPath);
        } else {
            runFilesRegistration(classPath);
        }
    }

    private static void runFilesRegistration(Path classPath) {
        try {
            FileRegistrar fileRegistrar = new PropertyFileRegistrar(propertyContext);
            FileRegistrationVisitor visitor = new FileRegistrationVisitor(fileRegistrar, FileRegistrar::register);

            log.debug("Begin property files registration");
            Files.walkFileTree(classPath.resolve(DEFAULT_RESOURCE_ROOT_FOLDER), visitor);
            log.debug("End property files registration");
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }

    private static void runFilesRegistrationWithinJar(Path classPath) {
        URI jarFileUri = URI.create("jar:file:" + classPath.toUri().getPath());
        try (FileSystem fileSystem = FileSystems.newFileSystem(jarFileUri, provideZipPropertiesForReading())) {
            Path rootPath = fileSystem.getPath("/");
            runFilesRegistration(rootPath);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
    }

    private static Map<String, String> provideZipPropertiesForReading() {
        Map<String, String> zipProperties = new HashMap<>();
        zipProperties.put("create", "false");
        zipProperties.put("encoding", "UTF-8");
        return zipProperties;
    }

    public static PropertyHolder resource(@NotNull String resourceName) {
        return propertyContext.getResource(resourceName);
    }
}
