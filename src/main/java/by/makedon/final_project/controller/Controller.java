package by.makedon.final_project.controller;

import by.makedon.final_project.connectionpool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyConnections();
        LOGGER.log(Level.INFO, "Destroy " + Controller.class);
    }

    @Override
    public void init() throws ServletException {
        ConnectionPool.getInstance();
        LOGGER.log(Level.INFO, "Init " + Controller.class);
    }
}
