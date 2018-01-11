package by.makedon.final_project.command;

public enum CommandType {
    EPAMLINK(new EpamLinkCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
