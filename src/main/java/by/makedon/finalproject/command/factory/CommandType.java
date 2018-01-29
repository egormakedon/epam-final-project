package by.makedon.finalproject.command.factory;

import by.makedon.finalproject.command.admincommand.DeleteUserCommand;
import by.makedon.finalproject.command.admincommand.DeleteUserPageCommand;
import by.makedon.finalproject.command.admincommand.RefreshStatementCommand;
import by.makedon.finalproject.command.admincommand.SetStatementCommand;
import by.makedon.finalproject.command.impl.AcceptRegistrationCommand;
import by.makedon.finalproject.command.impl.ChangePasswordCommand;
import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.command.impl.ForgotPasswordCommand;
import by.makedon.finalproject.command.impl.ForwardChangePasswordCommand;
import by.makedon.finalproject.command.impl.LoginCommand;
import by.makedon.finalproject.command.impl.LogoutCommand;
import by.makedon.finalproject.command.impl.ProfileCommand;
import by.makedon.finalproject.command.impl.RegistrationCommand;
import by.makedon.finalproject.command.usercommand.ChangeUserDataLinkCommand;
import by.makedon.finalproject.command.usercommand.ChangeUserDataPageCommand;
import by.makedon.finalproject.command.usercommand.ChangeUserDataCommand;
import by.makedon.finalproject.command.usercommand.FillFormCommand;
import by.makedon.finalproject.command.usercommand.RefreshFillFormCommand;
import by.makedon.finalproject.command.usercommand.SendFillFormCommand;
import by.makedon.finalproject.command.usercommand.ShowFormCommand;
import by.makedon.finalproject.logic.adminlogic.DeleteUserLogic;
import by.makedon.finalproject.logic.adminlogic.RefreshStatementLogic;
import by.makedon.finalproject.logic.adminlogic.SetStatementLogic;
import by.makedon.finalproject.logic.baselogic.AcceptRegistrationLogic;
import by.makedon.finalproject.logic.baselogic.ChangePasswordLogic;
import by.makedon.finalproject.logic.baselogic.ForgotPasswordLogic;
import by.makedon.finalproject.logic.baselogic.LoginLogic;
import by.makedon.finalproject.logic.baselogic.ProfileLogic;
import by.makedon.finalproject.logic.baselogic.RegistrationLogic;
import by.makedon.finalproject.logic.userlogic.ChangeUserDataLogic;
import by.makedon.finalproject.logic.userlogic.RefreshFillFormLogic;
import by.makedon.finalproject.logic.userlogic.SendFillFormLogic;
import by.makedon.finalproject.logic.userlogic.ShowFormLogic;

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
    CHANGEUSERDATAPAGE(new ChangeUserDataPageCommand()),

    CHANGEUSERDATA(new ChangeUserDataCommand(new ChangeUserDataLogic())),
    CHANGEUSERDATALINK(new ChangeUserDataLinkCommand()),

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
