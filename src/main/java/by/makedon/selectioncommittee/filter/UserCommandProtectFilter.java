package by.makedon.selectioncommittee.filter;

import by.makedon.selectioncommittee.command.factory.CommandType;
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

public class UserCommandProtectFilter implements Filter {
    private static final String COMMAND = "command";
    private static final String TYPE = "type";
    private static final String USER = "user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String commandValue = servletRequest.getParameter(COMMAND);
        if (commandValue != null) {
            commandValue = commandValue.toUpperCase();
            if (commandValue.equals(CommandType.FORWARDSENDFORM.toString()) || commandValue.equals(CommandType.SENDFORM.toString()) ||
                    commandValue.equals(CommandType.RESETFORM.toString()) || commandValue.equals(CommandType.FORWARDCHANGEUSERDATA.toString()) ||
                    commandValue.equals(CommandType.CHANGEUSERDATA.toString()) ||
                    commandValue.equals(CommandType.FORWARDCHANGEUSERDATALINK.toString()) ||
                    commandValue.equals(CommandType.CHANGEEMAIL.toString()) ||
                    commandValue.equals(CommandType.CHANGEUSERNAME.toString()) ||
                    commandValue.equals(CommandType.SHOWFORM.toString()) ||
                    commandValue.equals(CommandType.CHECKSTATUS.toString())) {

                HttpSession session = ((HttpServletRequest)servletRequest).getSession();
                String typeValue = (String) session.getAttribute(TYPE);
                if (!(typeValue != null && typeValue.equals(USER))) {
                    ((HttpServletResponse)servletResponse).sendRedirect(Page.WELCOME);
                    return;
                }
            }
        }

        doFilter(servletRequest, servletResponse, filterChain);
    }

    @Override
    public void destroy() {

    }
}
