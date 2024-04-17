package by.makedon.selectioncommittee.app.command.user;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.configuration.controller.Router;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterBuilder;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class ForwardChangeUserDataLinkCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ForwardChangeUserDataLinkCommand.class);

    private final Map<String, UnaryOperator<String>> typeChangerToPagePathFunctionMap;

    public ForwardChangeUserDataLinkCommand() {
        typeChangerToPagePathFunctionMap = new HashMap<>();

        typeChangerToPagePathFunctionMap.put(RequestParameterKey.EMAIL,
            usernameValue -> Page.FORWARD + "?" + RequestParameterBuilder
                .builder()
                .put(RequestParameterKey.USERNAME, usernameValue)
                .put(RequestParameterKey.PAGE_PATH, Page.CHANGE_EMAIL)
                .build());
        typeChangerToPagePathFunctionMap.put(RequestParameterKey.USERNAME,
            usernameValue -> Page.FORWARD + "?" + RequestParameterBuilder
                .builder()
                .put(RequestParameterKey.USERNAME, usernameValue)
                .put(RequestParameterKey.PAGE_PATH, Page.CHANGE_USERNAME)
                .build());
        typeChangerToPagePathFunctionMap.put(RequestParameterKey.PASSWORD,
            usernameValue -> Page.FORWARD + "?" + RequestParameterBuilder
                .builder()
                .put(RequestParameterKey.USERNAME, usernameValue)
                .put(RequestParameterKey.PAGE_PATH, Page.CHANGE_PASSWORD)
                .build());
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String usernameValue = request.getParameter(RequestParameterKey.USERNAME);
        String typeChangerValue = request.getParameter(RequestParameterKey.TYPE_CHANGER);

        logger.debug("Execute ForwardChangeUserDataLinkCommand: {}={}, {}={}",
            RequestParameterKey.USERNAME, usernameValue,
            RequestParameterKey.TYPE_CHANGER, typeChangerValue);

        Router router = Router.redirectRouter();
        String pagePath = Optional
            .ofNullable(typeChangerToPagePathFunctionMap.get(typeChangerValue))
            .map(pagePathFunction -> pagePathFunction.apply(usernameValue))
            .orElse(Page.MESSAGE + "?"
                + RequestParameterBuilder.builder().put(RequestParameterKey.MESSAGE, "invalid parameter").build());
        router.setPagePath(pagePath);
        return router;
    }
}
