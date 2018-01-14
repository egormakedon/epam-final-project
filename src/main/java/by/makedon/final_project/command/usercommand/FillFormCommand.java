package by.makedon.final_project.command.usercommand;

import by.makedon.final_project.command.Command;
import by.makedon.final_project.controller.Router;
import by.makedon.final_project.logic.userlogic.FillFormLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FillFormCommand implements Command {
    private static final String USERNAME = "username";
    private FillFormLogic logic;

    public FillFormCommand(FillFormLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String usernameValue = (String)session.getAttribute(USERNAME);
        return logic.doAction(usernameValue);
    }
}
