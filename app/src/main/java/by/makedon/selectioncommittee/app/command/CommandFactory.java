package by.makedon.selectioncommittee.app.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class CommandFactory {
    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);

    private CommandFactory() {
    }

    public static Optional<Command> getCommandBy(String commandTypeName) {
        try {
            CommandType commandType = CommandType.valueOf(commandTypeName.toUpperCase());
            return Optional.of(commandType.getCommand());
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
