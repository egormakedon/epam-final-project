package by.makedon.selectioncommittee.app.command;

import by.makedon.selectioncommittee.app.command.admin.*;
import by.makedon.selectioncommittee.app.command.base.*;
import by.makedon.selectioncommittee.app.command.user.*;
import by.makedon.selectioncommittee.app.logic.admin.*;
import by.makedon.selectioncommittee.app.logic.base.*;
import by.makedon.selectioncommittee.app.logic.user.*;

public enum CommandType {
    //---------- BASE ----------
    REGISTRATION(new RegistrationCommand(new RegistrationLogic())),
    LOGIN(new LoginCommand(new LoginLogic())),
    PROFILE(new ProfileCommand(new ProfileLogic())),
    LOGOUT(new LogoutCommand()),
    FORGOTPASSWORD(new ForgotPasswordCommand(new ForgotPasswordLogic())),
    FORWARDCHANGEPASSWORD(new ForwardToChangePasswordCommand()),
    CHANGEPASSWORD(new ChangePasswordCommand(new ChangePasswordLogic())),
    ACCEPTREGISTRATION(new AcceptRegistrationCommand(new AcceptRegistrationLogic())),
    CHANGELANG(new ChangeLangCommand()),
    //---------- BASE ----------

    //---------- USER ----------
    FORWARDSENDFORM(new ForwardSendFormCommand()),
    SENDFORM(new SendFormCommand(new SendFormLogic())),
    RESETFORM(new ResetFormCommand(new ResetFormLogic())),
    FORWARDCHANGEUSERDATA(new ForwardChangeUserDataCommand()),
    CHANGEUSERDATA(new ChangeUserDataCommand(new ChangeUserDataLogic())),
    FORWARDCHANGEUSERDATALINK(new ForwardChangeUserDataLinkCommand()),
    CHANGEEMAIL(new ChangeEmailCommand(new ChangeEmailLogic())),
    CHANGEUSERNAME(new ChangeUsernameCommand(new ChangeUsernameLogic())),
    SHOWFORM(new ShowFormCommand(new ShowFormLogic())),
    CHECKSTATUS(new CheckStatusCommand(new CheckStatusLogic())),
    //---------- USER ----------

    //---------- ADMIN ----------
    SETSTATEMENT(new SetStatementCommand(new SetStatementLogic())),
    RESETSTATEMENT(new ResetStatementCommand(new ResetStatementLogic())),
    FORWARDDELETEUSER(new ForwardDeleteUserCommand()),
    DELETEUSER(new DeleteUserCommand(new DeleteUserLogic())),
    FORWARDCHANGENUMBEROFSEATS(new ForwardChangeNumberOfSeatsCommand()),
    CHANGENUMBEROFSEATS(new ChangeNumberOfSeatsCommand(new ChangeNumberOfSeatsLogic())),
    SHOWENROLLEES(new ShowEnrolleesCommand(new ShowEnrolleesLogic())),
    SHOWSPECIALITYS(new ShowSpecialitysCommand(new ShowSpecialitysLogic()));
    //---------- ADMIN ----------

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
