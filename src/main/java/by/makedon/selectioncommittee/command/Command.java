package by.makedon.selectioncommittee.command;

import by.makedon.selectioncommittee.controller.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Logger LOGGER = LogManager.getLogger(Command.class);
    Router execute(HttpServletRequest req);
}