package by.makedon.selectioncommittee.app.configuration.filter;

import by.makedon.selectioncommittee.app.configuration.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionCreatorFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SessionCreatorFilter.class);
    private static final String INDEX_JSP = "/index.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //empty
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getServletPath().equals(INDEX_JSP)) {
            request.getSession(true);
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            request.getSession(true);
            response.sendRedirect(Page.WELCOME);
            return;
        } else if (session.isNew()) {
            response.sendRedirect(Page.WELCOME);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //empty
    }
}
