package by.makedon.final_project.command.factory;

import by.makedon.final_project.command.impl.AcceptRegistrationCommand;
import by.makedon.final_project.command.impl.ChangePasswordCommand;
import by.makedon.final_project.command.Command;
import by.makedon.final_project.command.impl.ForgotPasswordCommand;
import by.makedon.final_project.command.impl.ForwardChangePasswordCommand;
import by.makedon.final_project.command.impl.LoginCommand;
import by.makedon.final_project.command.impl.LogoutCommand;
import by.makedon.final_project.command.impl.ProfileCommand;
import by.makedon.final_project.command.impl.RegistrationCommand;
import by.makedon.final_project.logic.baselogic.AcceptRegistrationLogic;
import by.makedon.final_project.logic.baselogic.ChangePasswordLogic;
import by.makedon.final_project.logic.baselogic.ForgotPasswordLogic;
import by.makedon.final_project.logic.baselogic.LoginLogic;
import by.makedon.final_project.logic.baselogic.ProfileLogic;
import by.makedon.final_project.logic.baselogic.RegistrationLogic;

public enum CommandType {
    REGISTRATION(new RegistrationCommand(new RegistrationLogic())),
    LOGIN(new LoginCommand(new LoginLogic())),
    PROFILE(new ProfileCommand(new ProfileLogic())),
    LOGOUT(new LogoutCommand()),
    FORGOTPASSWORD(new ForgotPasswordCommand(new ForgotPasswordLogic())),
    FORWARDCHANGEPASSWORD(new ForwardChangePasswordCommand()),
    CHANGEPASSWORD(new ChangePasswordCommand(new ChangePasswordLogic())),
    ACCEPTREGISTRATION(new AcceptRegistrationCommand(new AcceptRegistrationLogic()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
