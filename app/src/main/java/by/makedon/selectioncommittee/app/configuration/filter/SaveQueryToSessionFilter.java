package by.makedon.selectioncommittee.app.configuration.filter;

import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveQueryToSessionFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SaveQueryToSessionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //empty
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String commandValue = request.getParameter(RequestParameterKey.COMMAND);

        logger.info("{}=`{}`", RequestParameterKey.COMMAND, commandValue);

        if (RequestParameterKey.CHANGE_LANG.equals(commandValue)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String lastPage = request.getServletPath() + "?" + request.getQueryString();
        HttpSession session = request.getSession();
        session.setAttribute(RequestParameterKey.LAST_PAGE, lastPage);

        logger.info("{}=`{}`", RequestParameterKey.LAST_PAGE, lastPage);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //empty
    }
}
