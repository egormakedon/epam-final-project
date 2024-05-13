package by.makedon.selectioncommittee.app.configuration.filter;

import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import by.makedon.selectioncommittee.app.dao.BaseDao;
import by.makedon.selectioncommittee.app.dao.impl.BaseDaoImpl;
import by.makedon.selectioncommittee.common.dao.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteUserFromSystemFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(DeleteUserFromSystemFilter.class);
    private final BaseDao baseDAO = BaseDaoImpl.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //empty
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String loginValue = (String) session.getAttribute(RequestParameterKey.LOGIN);
        logger.info("{}=`{}`", RequestParameterKey.LOGIN, loginValue);

        if (Boolean.TRUE.toString().equals(loginValue)) {
            String usernameValue = (String) session.getAttribute(RequestParameterKey.USERNAME);
            logger.info("{}=`{}`", RequestParameterKey.USERNAME, usernameValue);

            if (isUsernameNotMatched(usernameValue)) {
                session.invalidate();
                response.sendRedirect(Page.WELCOME);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isUsernameNotMatched(String usernameValue) {
        return !isUsernameMatched(usernameValue);
    }

    private boolean isUsernameMatched(String usernameValue) {
        try {
            return baseDAO.matchUsername(usernameValue);
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void destroy() {
        //empty
    }
}
