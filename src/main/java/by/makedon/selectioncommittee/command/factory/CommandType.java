package by.makedon.selectioncommittee.command.factory;

import by.makedon.selectioncommittee.command.admin.ChangeNumberOfSeatsCommand;
import by.makedon.selectioncommittee.command.admin.DeleteUserCommand;
import by.makedon.selectioncommittee.command.admin.DeleteUserPageCommand;
import by.makedon.selectioncommittee.command.admin.RefreshStatementCommand;
import by.makedon.selectioncommittee.command.admin.SetStatementCommand;
import by.makedon.selectioncommittee.command.base.AcceptRegistrationCommand;
import by.makedon.selectioncommittee.command.base.ChangePasswordCommand;
import by.makedon.selectioncommittee.command.Command;
import by.makedon.selectioncommittee.command.base.ForgotPasswordCommand;
import by.makedon.selectioncommittee.command.base.ForwardToChangePasswordCommand;
import by.makedon.selectioncommittee.command.base.LoginCommand;
import by.makedon.selectioncommittee.command.base.LogoutCommand;
import by.makedon.selectioncommittee.command.base.ProfileCommand;
import by.makedon.selectioncommittee.command.base.RegistrationCommand;
import by.makedon.selectioncommittee.command.user.ChangeEmailCommand;
import by.makedon.selectioncommittee.command.user.ChangeUserDataLinkCommand;
import by.makedon.selectioncommittee.command.user.ChangeUserDataPageCommand;
import by.makedon.selectioncommittee.command.user.ChangeUserDataCommand;
import by.makedon.selectioncommittee.command.user.ChangeUsernameCommand;
import by.makedon.selectioncommittee.command.user.ForwardSendFormCommand;
import by.makedon.selectioncommittee.command.user.RefreshFillFormCommand;
import by.makedon.selectioncommittee.command.user.SendFormCommand;
import by.makedon.selectioncommittee.command.user.ShowFormCommand;
import by.makedon.selectioncommittee.logic.admin.ChangeNumberOfSeatsLogic;
import by.makedon.selectioncommittee.logic.admin.DeleteUserLogic;
import by.makedon.selectioncommittee.logic.admin.RefreshStatementLogic;
import by.makedon.selectioncommittee.logic.admin.SetStatementLogic;
import by.makedon.selectioncommittee.logic.base.AcceptRegistrationLogic;
import by.makedon.selectioncommittee.logic.base.ChangePasswordLogic;
import by.makedon.selectioncommittee.logic.base.ForgotPasswordLogic;
import by.makedon.selectioncommittee.logic.base.LoginLogic;
import by.makedon.selectioncommittee.logic.base.ProfileLogic;
import by.makedon.selectioncommittee.logic.base.RegistrationLogic;
import by.makedon.selectioncommittee.logic.user.ChangeEmailLogic;
import by.makedon.selectioncommittee.logic.user.ChangeUserDataLogic;
import by.makedon.selectioncommittee.logic.user.ChangeUsernameLogic;
import by.makedon.selectioncommittee.logic.user.RefreshFillFormLogic;
import by.makedon.selectioncommittee.logic.user.SendFormLogic;
import by.makedon.selectioncommittee.logic.user.ShowFormLogic;

public enum CommandType {
    REGISTRATION(new RegistrationCommand(new RegistrationLogic())),
    LOGIN(new LoginCommand(new LoginLogic())),
    PROFILE(new ProfileCommand(new ProfileLogic())),
    LOGOUT(new LogoutCommand()),
    FORGOTPASSWORD(new ForgotPasswordCommand(new ForgotPasswordLogic())),
    FORWARDCHANGEPASSWORD(new ForwardToChangePasswordCommand()),
    CHANGEPASSWORD(new ChangePasswordCommand(new ChangePasswordLogic())),
    ACCEPTREGISTRATION(new AcceptRegistrationCommand(new AcceptRegistrationLogic())),

    FORWARDSENDFORM(new ForwardSendFormCommand()),
    SENDFORM(new SendFormCommand(new SendFormLogic())),
    REFRESHFILLFORM(new RefreshFillFormCommand(new RefreshFillFormLogic())),
    SHOWFORMPAGE(new ShowFormCommand(new ShowFormLogic())),
    CHANGEUSERDATAPAGE(new ChangeUserDataPageCommand()),
    CHANGEUSERDATA(new ChangeUserDataCommand(new ChangeUserDataLogic())),
    CHANGEUSERDATALINK(new ChangeUserDataLinkCommand()),
    CHANGEEMAIL(new ChangeEmailCommand(new ChangeEmailLogic())),
    CHANGEUSERNAME(new ChangeUsernameCommand(new ChangeUsernameLogic())),
    DELETE_USER_PAGE(new DeleteUserPageCommand()),
    DELETE_USER(new DeleteUserCommand(new DeleteUserLogic())),
    REFRESH_STATEMENT(new RefreshStatementCommand(new RefreshStatementLogic())),
    SET_STATEMENT(new SetStatementCommand(new SetStatementLogic())),
    CHANGE_NUMBER_OF_SEATS(new ChangeNumberOfSeatsCommand(new ChangeNumberOfSeatsLogic()));

    private Command command;
    CommandType(Command command) {
        this.command = command;
    }
    public Command getCommand() {
        return command;
    }
}