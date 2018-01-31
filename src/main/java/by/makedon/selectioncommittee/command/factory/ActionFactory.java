package by.makedon.selectioncommittee.command.factory;

import by.makedon.selectioncommittee.command.Command;

import java.util.Optional;

public class ActionFactory {
    public static Optional<Command> defineCommand(String commandName) {
        CommandType type = CommandType.valueOf(commandName.toUpperCase());
        return Optional.of(type.getCommand());
    }
}