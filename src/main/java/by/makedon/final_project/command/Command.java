package by.makedon.final_project.command;

import by.makedon.final_project.controller.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest req);
}