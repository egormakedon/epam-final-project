package by.makedon.selectioncommittee.app.command;

import by.makedon.selectioncommittee.app.configuration.controller.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request);
}
