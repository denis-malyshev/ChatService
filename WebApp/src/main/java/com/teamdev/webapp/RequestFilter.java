package com.teamdev.webapp;

import com.teamdev.business.implement.error.AuthenticationError;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebFilter
public class RequestFilter implements Filter {

    private ServiceProvider services;
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        services = ServiceProvider.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (filterConfig == null) {
            return;
        }
        ServletContext servletContext = filterConfig.getServletContext();
        String token = servletRequest.getParameter("token");
        try {
            if (services.getTokenService().isValid(token)) {
                RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/chats");
                dispatcher.forward(servletRequest, servletResponse);
            } else {

            }
        } catch (AuthenticationError authenticationError) {
            authenticationError.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
