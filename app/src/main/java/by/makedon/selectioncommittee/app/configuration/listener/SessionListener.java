package by.makedon.selectioncommittee.app.configuration.listener;

import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

public class SessionListener implements HttpSessionListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);
    private static final String EN = "en";

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setAttribute(RequestParameterKey.LANG, EN);
        session.setAttribute(RequestParameterKey.LOGIN, Boolean.FALSE.toString());
        session.setAttribute(RequestParameterKey.LAST_PAGE, Page.WELCOME);

        logger.info("Session is created: {}", session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attribute = attributeNames.nextElement();
            session.removeAttribute(attribute);
        }

        logger.info("Session is destroyed: {}", session.getId());
    }
}
