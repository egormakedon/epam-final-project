package by.makedon.selectioncommittee.common.resource;

import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

/**
 * @author Yahor Makedon
 */
interface FileRegistrar {
    void register(@Nullable Path file);
}
