package by.makedon.final_project.command;

import by.makedon.final_project.constant.PageConstant;
import by.makedon.final_project.controller.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest req);
    default void errorPrint(HttpServletRequest req, Router router, Logger logger, Exception e) {
        logger.log(Level.ERROR, e);
        req.setAttribute("error", e);
        router.setRoute(Router.RouteType.FORWARD);
        router.setPagePath(PageConstant.MESSAGE_PAGE);
    }
}