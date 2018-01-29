package by.makedon.finalproject.controller;

import by.makedon.finalproject.command.factory.ActionFactory;
import by.makedon.finalproject.command.Command;
import by.makedon.finalproject.connectionpool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private static final String COMMAND_LITERAL = "command";

    @Override
    public void init() throws ServletException {
        ConnectionPool.getInstance();
        LOGGER.log(Level.INFO, "Init " + Controller.class);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyConnections();
        LOGGER.log(Level.INFO, "Destroy " + Controller.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Command> commandOptional = ActionFactory.defineCommand(req.getParameter(COMMAND_LITERAL));
        Command command = commandOptional.get();
        Router router = command.execute(req);

        if (router.getRoute() == Router.RouteType.FORWARD) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(router.getPagePath());
            dispatcher.forward(req, resp);
        } else {
            resp.sendRedirect(router.getPagePath());
        }
    }
}
