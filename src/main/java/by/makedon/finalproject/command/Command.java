package by.makedon.finalproject.command;

import by.makedon.finalproject.controller.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest req);
}