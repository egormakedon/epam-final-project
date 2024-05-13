package by.makedon.selectioncommittee.app.configuration.controller;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.command.CommandFactory;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.common.connectionpool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class Controller extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Override
    public void init() throws ServletException {
        ConnectionPool.getInstance();
        logger.info("Connection Pool has been initialized");
        logger.info("Front-Controller Servlet has been initialized");
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroy();
        logger.info("Connection Pool has been destroyed");
        logger.info("Front-Controller Servlet has been destroyed");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Processing GET");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Processing POST");
        processRequest(req, resp);
    }

    private static void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Router router = extractCommandFrom(request)
            .map(command -> command.execute(request))
            .filter(Controller::isRouterValid)
            .orElse(null);

        if (router == null) {
            logger.warn("Router is incorrect. Make redirection to the Welcome page..");
            response.sendRedirect(Page.WELCOME);
            return;
        }

        submitRouter(router, request, response);
    }

    private static Optional<Command> extractCommandFrom(HttpServletRequest request) {
        String commandTypeName = request.getParameter(RequestParameterKey.COMMAND);
        logger.debug("Provided commandType: `{}`", commandTypeName);

        Optional<Command> commandOptional = CommandFactory.getCommandBy(commandTypeName);
        if (!commandOptional.isPresent()) {
            logger.warn("Command does not exist by provided commandType: `{}`", commandTypeName);
        }

        return commandOptional;
    }

    private static boolean isRouterValid(Router router) {
        if (Objects.isNull(router)) {
            return false;
        }
        if (!router.getPagePath().isPresent()) {
            logger.warn("There is no provided pagePath!!");
            return false;
        }
        return true;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static void submitRouter(Router router, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pagePath = router.getPagePath().get();
        if (router.isForwardRouter()) {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } else if (router.isRedirectRouter()) {
            response.sendRedirect(pagePath);
        } else {
            logger.warn("Unknown router: `{}`. Make redirection to the Welcome page..", router);
            response.sendRedirect(Page.WELCOME);
        }
    }
}
