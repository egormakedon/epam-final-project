package by.makedon.final_project.command;

import by.makedon.final_project.controller.Router;
import by.makedon.final_project.logic.RegistrationLogic;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private RegistrationLogic logic;

    RegistrationCommand(RegistrationLogic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        return null;
    }
}
