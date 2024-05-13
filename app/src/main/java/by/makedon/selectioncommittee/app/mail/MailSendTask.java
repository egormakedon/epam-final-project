package by.makedon.selectioncommittee.app.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class MailSendTask implements Runnable {
    @NotNull
    private final Mail mail;

    @Override
    public void run() {
        Optional<MimeMessage> message = createMessageFrom(mail);
        if (message.isPresent()) {
            sendMessage(message.get());
        } else {
            log.error(String.format("Message has not been sent! Invalid mail: %s", mail));
        }
    }

    private static Optional<MimeMessage> createMessageFrom(@NotNull Mail mail) {
        Objects.requireNonNull(mail);

        Session mailSession = MailSessionProvider.getSession();
        mailSession.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(mail.getSubject());
            message.setContent(mail.getContent(), "text/html");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getSendToEmail()));
            return Optional.of(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    private static void sendMessage(@NotNull MimeMessage message) {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
