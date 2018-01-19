package by.makedon.final_project.listener;

import by.makedon.final_project.constant.LoginState;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute("lang", "en");
        session.setAttribute("login", LoginState.FALSE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attribute = attributeNames.nextElement();
            session.removeAttribute(attribute);
        }
    }
}