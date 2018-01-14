package by.makedon.final_project.command.usercommand;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.logic.userlogic.FillFormLogic;

import javax.servlet.http.HttpServletRequest;

public class FillFormCommand implements Command {
    private FillFormLogic logic;

    public FillFormCommand(FillFormLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        return logic.doAction();
    }
}
