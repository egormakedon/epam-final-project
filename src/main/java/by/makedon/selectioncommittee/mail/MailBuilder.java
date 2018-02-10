package by.makedon.selectioncommittee.mail;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class MailBuilder {
    private static final Logger LOGGER = LogManager.getLogger(MailBuilder.class);

    private String templatePath;

    public MailBuilder(String templatePath) {
        this.templatePath = templatePath;
    }

    public String takeMailTemplate() {
        URL url = this.getClass().getClassLoader().getResource(templatePath);
        if (url == null) {
            LOGGER.log(Level.FATAL, "mail template file hasn't found");
            throw new RuntimeException("mail template file hasn't found");
        }

        StringBuilder mailTextBuilder = new StringBuilder();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(url.toURI()));
            while (scanner.hasNextLine()) {
                mailTextBuilder.append(scanner.nextLine());
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            LOGGER.log(Level.FATAL, e);
            throw new RuntimeException(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return mailTextBuilder.toString();
    }
}
