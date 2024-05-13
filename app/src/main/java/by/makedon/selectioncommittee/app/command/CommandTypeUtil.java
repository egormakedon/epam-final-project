package by.makedon.selectioncommittee.app.command;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Yahor Makedon
 */
public final class CommandTypeUtil {
    private static final Set<CommandType> adminCommandTypes = EnumSet.of(
        CommandType.SETSTATEMENT, CommandType.RESETSTATEMENT,
        CommandType.FORWARDDELETEUSER, CommandType.DELETEUSER,
        CommandType.FORWARDCHANGENUMBEROFSEATS, CommandType.CHANGENUMBEROFSEATS,
        CommandType.SHOWENROLLEES, CommandType.SHOWSPECIALITYS);

    private static final Set<CommandType> userCommandTypes = EnumSet.of(
        CommandType.FORWARDSENDFORM, CommandType.SENDFORM,
        CommandType.RESETFORM, CommandType.FORWARDCHANGEUSERDATA,
        CommandType.CHANGEUSERDATA, CommandType.FORWARDCHANGEUSERDATALINK,
        CommandType.CHANGEEMAIL, CommandType.CHANGEUSERNAME,
        CommandType.SHOWFORM, CommandType.CHECKSTATUS);

    public boolean isAdminCommand(CommandType commandType) {
        return adminCommandTypes.contains(commandType);
    }

    public boolean isUserCommand(CommandType commandType) {
        return userCommandTypes.contains(commandType);
    }
}
