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

public class AdminCommandProtectFilter implements Filter {
    private static final String COMMAND = "command";
    private static final String TYPE = "type";
    private static final String ADMIN = "admin";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String commandValue = servletRequest.getParameter(COMMAND);
        if (commandValue != null) {
            commandValue = commandValue.toUpperCase();
            if (commandValue.equals(CommandType.SETSTATEMENT.toString()) || commandValue.equals(CommandType.RESETSTATEMENT.toString()) ||
                    commandValue.equals(CommandType.FORWARDDELETEUSER.toString()) || commandValue.equals(CommandType.DELETEUSER.toString()) ||
                    commandValue.equals(CommandType.FORWARDCHANGENUMBEROFSEATS.toString()) ||
                    commandValue.equals(CommandType.CHANGENUMBEROFSEATS.toString())) {

                HttpSession session = ((HttpServletRequest)servletRequest).getSession();
                String typeValue = (String) session.getAttribute(TYPE);
                if (!(typeValue != null && typeValue.equals(ADMIN))) {
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
