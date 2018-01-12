package by.makedon.final_project.command;

import by.makedon.final_project.logic.RegistrationLogic;

public enum CommandType {
    EPAMLINK(new EpamLinkCommand()),
    REGISTRATION(new RegistrationCommand(new RegistrationLogic()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
