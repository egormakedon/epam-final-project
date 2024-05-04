package by.makedon.selectioncommittee.app.mail;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author Yahor Makedon
 */
@Slf4j
public class MailTemplateReader {
    public String readMailTemplateBy(@NotNull String templatePath) {
        Objects.requireNonNull(templatePath);
        URL templateUrl = getTemplateUrlBy(templatePath);
        return readTemplateBy(templateUrl);
    }

    private URL getTemplateUrlBy(String templatePath) {
        return Optional
            .ofNullable(this.getClass().getClassLoader().getResource(templatePath))
            .orElseThrow(() -> {
                final String message = String.format("%s - mail template has not been found", templatePath);
                log.error(message);
                throw new MailTemplateReaderException(message);
            });
    }

    private String readTemplateBy(URL templateUrl) {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(templateUrl.toURI()))) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new MailTemplateReaderException(e.getMessage(), e);
        }
        return sb.toString();
    }
}
