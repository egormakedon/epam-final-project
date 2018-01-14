package by.makedon.final_project.listener;

import by.makedon.final_project.constant.LoginState;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("lang", "en");
        se.getSession().setAttribute("login", LoginState.FALSE);
    }
}
