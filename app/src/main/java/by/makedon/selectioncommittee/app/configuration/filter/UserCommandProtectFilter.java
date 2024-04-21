package by.makedon.selectioncommittee.app.configuration.filter;

import by.makedon.selectioncommittee.app.command.CommandFactory;
import by.makedon.selectioncommittee.app.command.CommandType;
import by.makedon.selectioncommittee.app.command.CommandTypeUtil;
import by.makedon.selectioncommittee.app.configuration.util.Page;
import by.makedon.selectioncommittee.app.configuration.util.RequestParameterKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserCommandProtectFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserCommandProtectFilter.class);
    private final CommandTypeUtil commandTypeUtil = new CommandTypeUtil();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //empty
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String commandValue = servletRequest.getParameter(RequestParameterKey.COMMAND);
        CommandType commandType = CommandFactory.getCommandTypeBy(commandValue).orElse(null);

        logger.info("{}=`{}`, commandType=`{}`", RequestParameterKey.COMMAND, commandValue, commandType);

        if (commandTypeUtil.isUserCommand(commandType)) {
            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
            String typeValue = (String) session.getAttribute(RequestParameterKey.TYPE);
            logger.info("{}=`{}`", RequestParameterKey.TYPE, typeValue);

            if (!RequestParameterKey.USER.equals(typeValue)) {
                ((HttpServletResponse) servletResponse).sendRedirect(Page.WELCOME);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //empty
    }
}
