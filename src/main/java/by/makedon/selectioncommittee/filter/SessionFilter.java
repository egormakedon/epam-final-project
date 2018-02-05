package by.makedon.selectioncommittee.filter;

import by.makedon.selectioncommittee.constant.Page;

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

public class SessionFilter implements Filter {
    private static final String INDEX_JSP = "/index.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        if (req.getServletPath().equals(INDEX_JSP)) {
            req.getSession(true);
            filterChain.doFilter(req, res);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null) {
            req.getSession(true);
            res.sendRedirect(Page.WELCOME);
            return;
        } else if (session.isNew()) {
            res.sendRedirect(Page.WELCOME);
            return;
        }
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}