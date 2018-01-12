package by.makedon.final_project.command;

import by.makedon.final_project.controller.Router;
import by.makedon.final_project.logic.ProfileLogic;

import javax.servlet.http.HttpServletRequest;

public class ProfileCommand implements Command {
    private static final String LOGIN = "login";
    private static final String USER_TYPE = "type";
    private ProfileLogic logic;

    ProfileCommand(ProfileLogic logic) {
        this.logic=logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String loginValue = (String)req.getSession().getAttribute(LOGIN);
        String typeValue = (String)req.getSession().getAttribute(USER_TYPE);
        return logic.doAction(loginValue, typeValue);
    }
}
