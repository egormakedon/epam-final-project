package by.makedon.selectioncommittee.common.resource;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiConsumer;

/**
 * @author Yahor Makedon
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
class FileRegistrationVisitor extends SimpleFileVisitor<Path> {
    private final FileRegistrar fileRegistrar;
    private final BiConsumer<FileRegistrar, Path> fileRegistrarPathBiConsumer;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        log.debug("Visit file: {}", file.getFileName());
        fileRegistrarPathBiConsumer.accept(fileRegistrar, file);
        return FileVisitResult.CONTINUE;
    }
}
