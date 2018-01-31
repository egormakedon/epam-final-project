package by.makedon.selectioncommittee.command;

import by.makedon.selectioncommittee.controller.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest req);
}