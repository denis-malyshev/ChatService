package com.teamdev.webapp;

import com.teamdev.business.implement.error.AuthenticationError;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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

        Map<String, String[]> parameterMap = servletRequest.getParameterMap();

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (!parameterMap.containsKey("token")) {
            response.sendError(403, "Can't find the token.");
            return;
        }

        if (!parameterMap.containsKey("userId")) {
            response.sendError(403, "Can't find the userId.");
            return;
        }

        String token = parameterMap.get("token")[0];
        long userId = Long.parseLong(parameterMap.get("userId")[0]);

        if (services.getUserService().findById(userId) == null) {
            response.sendError(403, "User with this id not existing.");
            return;
        }

        try {
            if (services.getTokenService().isValid(token)) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (AuthenticationError authenticationError) {

            if (authenticationError.getMessage().equals("Invalid token key.")) {
                response.sendError(403, "Invalid token key.");
            }
            if (authenticationError.getMessage().equals("Token has been expired.")) {
                response.sendError(403, "Token has been expired.");
            }
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
