package by.makedon.final_project.command.factory;

import by.makedon.final_project.command.admincommand.DeleteUserCommand;
import by.makedon.final_project.command.admincommand.DeleteUserPageCommand;
import by.makedon.final_project.command.admincommand.RefreshStatementCommand;
import by.makedon.final_project.command.admincommand.SetStatementCommand;
import by.makedon.final_project.command.impl.AcceptRegistrationCommand;
import by.makedon.final_project.command.impl.ChangePasswordCommand;
import by.makedon.final_project.command.Command;
import by.makedon.final_project.command.impl.ForgotPasswordCommand;
import by.makedon.final_project.command.impl.ForwardChangePasswordCommand;
import by.makedon.final_project.command.impl.LoginCommand;
import by.makedon.final_project.command.impl.LogoutCommand;
import by.makedon.final_project.command.impl.ProfileCommand;
import by.makedon.final_project.command.impl.RegistrationCommand;
import by.makedon.final_project.command.usercommand.FillFormCommand;
import by.makedon.final_project.command.usercommand.RefreshFillFormCommand;
import by.makedon.final_project.command.usercommand.SendFillFormCommand;
import by.makedon.final_project.command.usercommand.ShowFormCommand;
import by.makedon.final_project.logic.adminlogic.DeleteUserLogic;
import by.makedon.final_project.logic.adminlogic.RefreshStatementLogic;
import by.makedon.final_project.logic.adminlogic.SetStatementLogic;
import by.makedon.final_project.logic.baselogic.AcceptRegistrationLogic;
import by.makedon.final_project.logic.baselogic.ChangePasswordLogic;
import by.makedon.final_project.logic.baselogic.ForgotPasswordLogic;
import by.makedon.final_project.logic.baselogic.LoginLogic;
import by.makedon.final_project.logic.baselogic.ProfileLogic;
import by.makedon.final_project.logic.baselogic.RegistrationLogic;
import by.makedon.final_project.logic.userlogic.RefreshFillFormLogic;
import by.makedon.final_project.logic.userlogic.SendFillFormLogic;
import by.makedon.final_project.logic.userlogic.ShowFormLogic;

public enum CommandType {
    REGISTRATION(new RegistrationCommand(new RegistrationLogic())),
    LOGIN(new LoginCommand(new LoginLogic())),
    PROFILE(new ProfileCommand(new ProfileLogic())),
    LOGOUT(new LogoutCommand()),
    FORGOTPASSWORD(new ForgotPasswordCommand(new ForgotPasswordLogic())),
    FORWARDCHANGEPASSWORD(new ForwardChangePasswordCommand()),
    CHANGEPASSWORD(new ChangePasswordCommand(new ChangePasswordLogic())),
    ACCEPTREGISTRATION(new AcceptRegistrationCommand(new AcceptRegistrationLogic())),

    FILLFORMPAGE(new FillFormCommand()),
    REFRESHFILLFORM(new RefreshFillFormCommand(new RefreshFillFormLogic())),
    SENDFILLFORM(new SendFillFormCommand(new SendFillFormLogic())),
    SHOWFORMPAGE(new ShowFormCommand(new ShowFormLogic())),

    DELETE_USER_PAGE(new DeleteUserPageCommand()),
    DELETE_USER(new DeleteUserCommand(new DeleteUserLogic())),
    REFRESH_STATEMENT(new RefreshStatementCommand(new RefreshStatementLogic())),
    SET_STATEMENT(new SetStatementCommand(new SetStatementLogic()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
