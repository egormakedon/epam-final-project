package by.makedon.selectioncommittee.app.configuration.controller;

import by.makedon.selectioncommittee.app.command.Command;
import by.makedon.selectioncommittee.app.command.CommandFactory;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.common.connectionpool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        String commandTypeName = request.getParameter(RequestParameterKey.COMMAND);
        logger.debug("Provided commandType=`{}`", commandTypeName);

        Optional<Command> commandOptional = CommandFactory.getCommandBy(commandTypeName);
        if (!commandOptional.isPresent()) {
            logger.warn("`{}` commandType does not exist", commandTypeName);
            return;
        }
        Command command = commandOptional.get();

        Router router = command.execute(request);

        Optional<String> pagePathOptional = router.getPagePath();
        if (!pagePathOptional.isPresent()) {
            logger.warn("There is no provided pagePath");
            return;
        }
        String pagePath = pagePathOptional.get();

        if (router.isForwardRouter()) {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } else if (router.isRedirectRouter()) {
            response.sendRedirect(pagePath);
        }
    }
}
