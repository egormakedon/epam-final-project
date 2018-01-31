package by.makedon.selectioncommittee.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

public class SessionListener implements HttpSessionListener {
    private static final String LANG = "lang";
    private static final String LOGIN = "login";
    private static final String EN = "en";
    private static final String FALSE = "false";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(LANG, EN);
        session.setAttribute(LOGIN, FALSE);
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