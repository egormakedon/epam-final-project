package by.makedon.selectioncommittee.app.configuration.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveQueryToSessionFilter implements Filter{
    private static final String LAST_PAGE = "lastPage";
    private static final String COMMAND = "command";
    private static final String CHANGE_LANG = "changelang";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String commandValue = req.getParameter(COMMAND);
        if (commandValue != null && commandValue.equals(CHANGE_LANG)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String lastPage = req.getServletPath() + "?" + req.getQueryString();
        HttpSession session = req.getSession();
        session.setAttribute(LAST_PAGE, lastPage);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
