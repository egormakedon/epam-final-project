package by.makedon.selectioncommittee.app.configuration.filter;

import by.makedon.selectioncommittee.app.dao.base.BaseDAOImpl;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.dao.base.BaseDAO;
import by.makedon.selectioncommittee.app.dao.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteUserFromSystemFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserFromSystemFilter.class);

    private static final String USERNAME = "username";
    private static final String LOGIN = "login";
    private static final String TRUE = "true";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        String loginValue = (String)session.getAttribute(LOGIN);
        if (loginValue != null && loginValue.equals(TRUE)) {
            String usernameValue = (String)session.getAttribute(USERNAME);

            BaseDAO dao = BaseDAOImpl.getInstance();
            try {
                if (!dao.matchUsername(usernameValue)) {
                    session.invalidate();
                    res.sendRedirect(Page.WELCOME);
                    return;
                }
            } catch (DAOException e) {
                LOGGER.log(Level.ERROR, e);
                session.invalidate();
                res.sendRedirect(Page.WELCOME);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
