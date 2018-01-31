package by.makedon.selectioncommittee.filter;

import by.makedon.selectioncommittee.constant.PageConstant;
import by.makedon.selectioncommittee.dao.basedao.BaseDAO;
import by.makedon.selectioncommittee.dao.basedao.LoginDAO;
import by.makedon.selectioncommittee.exception.DAOException;
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
    private static final String USERNAME = "username";
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserFromSystemFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        String usernameValue = (String) session.getAttribute(USERNAME);
        if (usernameValue == null) {
            session.invalidate();
            req.setAttribute("message", "session failed");
            req.getRequestDispatcher(PageConstant.MESSAGE_PAGE).forward(req, res);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        BaseDAO dao = LoginDAO.getInstance();
        try {
            boolean result = dao.isUsernameExist(usernameValue);
            if (!result) {
                session.invalidate();
                res.sendRedirect(PageConstant.MESSAGE_PAGE + "?message=" + usernameValue + " was deleted from the system");
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (DAOException e) {
            session.invalidate();
            LOGGER.log(Level.ERROR, e);
            res.sendRedirect(PageConstant.MESSAGE_PAGE + "?message=" + e.getMessage());
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
