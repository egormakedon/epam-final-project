package by.makedon.selectioncommittee.app.command.base;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterBuilder;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.entity.UserType;
import by.makedon.selectioncommittee.app.logic.Logic;
import by.makedon.selectioncommittee.app.logic.LogicException;
import by.makedon.selectioncommittee.app.logic.base.LoginLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class LoginCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LoginCommand.class);

    private final Logic logic;

    public LoginCommand(Logic logic) {
        this.logic = logic;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String usernameValue = request.getParameter(RequestParameterKey.USERNAME);
        String passwordValue = request.getParameter(RequestParameterKey.PASSWORD);

        logger.debug("Execute LoginCommand: {}={}, {}={}",
            RequestParameterKey.USERNAME, usernameValue,
            RequestParameterKey.PASSWORD, "*******");

        Router router = Router.redirectRouter();
        try {
            logic.doAction(Arrays.asList(usernameValue, passwordValue));

            LoginLogic loginLogic = (LoginLogic) logic;
            String type = loginLogic.getType();

            HttpSession session = request.getSession();
            session.setAttribute(RequestParameterKey.USERNAME, usernameValue);
            session.setAttribute(RequestParameterKey.TYPE, type);
            session.setAttribute(RequestParameterKey.LOGIN, Boolean.TRUE.toString());

            UserType userType = UserType.of(type);
            RequestParameterBuilder parameterBuilder = RequestParameterBuilder.builder();
            if (userType.isAdmin()) {
                parameterBuilder.put(RequestParameterKey.PAGE_PATH, Page.ADMIN);
            } else if (userType.isUser()) {
                parameterBuilder.put(RequestParameterKey.PAGE_PATH, Page.USER);
            } else {
                logger.warn(String.format("Unknown userType=`%s` for redirection", userType));
                parameterBuilder.put(RequestParameterKey.PAGE_PATH, Page.WELCOME);
            }
            router.setPagePath(Page.FORWARD + "?" + parameterBuilder.build());
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            String requestParameters = RequestParameterBuilder
                .builder()
                .put(RequestParameterKey.MESSAGE, e.getMessage())
                .build();
            router.setPagePath(Page.MESSAGE + "?" + requestParameters);
        }

        return router;
    }
}
