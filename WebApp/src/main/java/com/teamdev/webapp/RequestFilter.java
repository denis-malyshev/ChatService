package com.teamdev.webapp;

import com.teamdev.business.UserService;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserId;
import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.dom.AuthenticationToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class RequestFilter implements Filter {

    private ContextProvider contextProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        contextProvider = ContextProvider.getInstance();
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

        Token token = new Token(parameterMap.get("token")[0]);
        UserId userId = new UserId(Long.parseLong(parameterMap.get("userId")[0]));

        UserService userService = contextProvider.getContext().getBean(UserService.class);

        if (userService.findById(userId) == null) {
            response.sendError(403, "User with this id not existing.");
            return;
        }

        AuthenticationTokenRepository tokenRepository = contextProvider.getContext().getBean(AuthenticationTokenRepository.class);

        AuthenticationToken innerToken = tokenRepository.findByKey(token.getToken());

        if (innerToken == null || innerToken.getUserId() != userId.getId()) {
            response.sendError(403, "Access denied.");
            return;
        }

        if (innerToken.getExpirationTime().compareTo(LocalDateTime.now()) < 1) {
            response.sendError(403, "Token has been expired.");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
