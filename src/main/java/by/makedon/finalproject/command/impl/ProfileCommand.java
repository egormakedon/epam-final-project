package by.makedon.finalproject.command.impl;

import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.controller.Router;
import by.makedon.finalproject.logic.baselogic.ProfileLogic;

import javax.servlet.http.HttpServletRequest;

public class ProfileCommand implements Command {
    private static final String LOGIN = "login";
    private static final String USER_TYPE = "type";
    private ProfileLogic logic;

    public ProfileCommand(ProfileLogic logic) {
        this.logic=logic;
    }

    @Override
    public Router execute(HttpServletRequest req) {
        String loginValue = (String)req.getSession().getAttribute(LOGIN);
        String typeValue = (String)req.getSession().getAttribute(USER_TYPE);
        return logic.doAction(loginValue, typeValue);
    }
}
