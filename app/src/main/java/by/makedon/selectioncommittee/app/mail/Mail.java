package by.makedon.selectioncommittee.app.mail;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Yahor Makedon
 */
@Data
public class Mail {
    private final String sendToEmail;
    private final String subject;
    private final String content;

    public Mail(@NotNull String sendToEmail, @NotNull String subject, @NotNull String content) {
        Objects.requireNonNull(sendToEmail);
        Objects.requireNonNull(subject);
        Objects.requireNonNull(content);

        this.sendToEmail = sendToEmail;
        this.subject = subject;
        this.content = content;
    }
}
