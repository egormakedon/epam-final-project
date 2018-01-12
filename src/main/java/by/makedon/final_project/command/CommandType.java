package by.makedon.final_project.command;

import by.makedon.final_project.logic.LoginLogic;
import by.makedon.final_project.logic.RegistrationLogic;

public enum CommandType {
    REGISTRATION(new RegistrationCommand(new RegistrationLogic())),
    LOGIN(new LoginCommand(new LoginLogic()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
