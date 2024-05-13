package by.makedon.selectioncommittee.app.mail;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * @author Yahor Makedon
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class MailTemplateFactory {
    private static final ReentrantLock readMailTemplateLock = new ReentrantLock();

    private static final Map<MailTemplateType, String> templateTypeToTemplateMap = new HashMap<>();

    public static String getMailTemplateBy(@NotNull MailTemplateType mailTemplateType) {
        Objects.requireNonNull(mailTemplateType);
        return templateTypeToTemplateMap.computeIfAbsent(mailTemplateType, templateTypeToTemplateFunction());
    }

    private static Function<MailTemplateType, String> templateTypeToTemplateFunction() {
        return MailTemplateFactory::readMailTemplateBy;
    }

    private static String readMailTemplateBy(@NotNull MailTemplateType mailTemplateType) {
        Objects.requireNonNull(mailTemplateType);

        readMailTemplateLock.lock();
        log.debug("MailTemplateFactory: read mail template lock!");
        try {
            return Optional
                .ofNullable(templateTypeToTemplateMap.get(mailTemplateType))
                .orElseGet(() -> {
                    MailTemplateReader templateReader = new MailTemplateReader();
                    return templateReader.readMailTemplateBy(mailTemplateType.getTemplatePath());
                });
        } finally {
            readMailTemplateLock.unlock();
            log.debug("MailTemplateFactory: read mail template unlock!");
        }
    }
}
