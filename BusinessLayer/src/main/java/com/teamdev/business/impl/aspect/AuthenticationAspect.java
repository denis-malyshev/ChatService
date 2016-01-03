package com.teamdev.business.impl.aspect;

import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.tinytypes.Token;
import com.teamdev.business.tinytypes.UserId;
import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.dom.AuthenticationToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuthenticationAspect {

    @Autowired
    private AuthenticationTokenRepository tokenRepository;

    @Pointcut("execution (* com.teamdev.business.*.*" +
            "(com.teamdev.business.tinytypes.Token,com.teamdev.business.tinytypes.UserId,..))")
    private void authPointcut() {
    }

    @Around("authPointcut()")
    public void authentication(ProceedingJoinPoint joinPoint) throws Throwable {

        Token token = (Token) joinPoint.getArgs()[0];
        UserId userId = (UserId) joinPoint.getArgs()[1];

        AuthenticationToken innerToken = tokenRepository.findByKey(token.getToken());

        if (innerToken == null || innerToken.getUserId() != userId.getId()) {
            throw new AuthenticationException("Invalid token.");
        }
        if (innerToken.getExpirationTime().compareTo(LocalDateTime.now()) < 1) {
            throw new AuthenticationException("Token has been expired.");
        }

        joinPoint.proceed();
    }
}
