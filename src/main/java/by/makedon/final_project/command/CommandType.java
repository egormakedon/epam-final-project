package by.makedon.final_project.command;

import by.makedon.final_project.logic.LoginLogic;
import by.makedon.final_project.logic.ProfileLogic;
import by.makedon.final_project.logic.RegistrationLogic;

public enum CommandType {
    REGISTRATION(new RegistrationCommand(new RegistrationLogic())),
    LOGIN(new LoginCommand(new LoginLogic())),
    PROFILE(new ProfileCommand(new ProfileLogic()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
