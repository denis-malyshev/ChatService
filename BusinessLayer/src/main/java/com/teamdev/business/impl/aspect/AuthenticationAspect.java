package com.teamdev.business.impl.aspect;

import com.teamdev.business.impl.exception.AuthenticationException;
import com.teamdev.business.impl.dto.Token;
import com.teamdev.business.impl.dto.UserId;
import com.teamdev.persistence.AuthenticationTokenRepository;
import com.teamdev.persistence.dom.AuthenticationToken;
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
            "(com.teamdev.business.impl.dto.Token,com.teamdev.business.impl.dto.UserId,..))")
    private void authPointcut() {
    }

    @Around("authPointcut()")
    public void authentication(ProceedingJoinPoint joinPoint) throws Throwable {

        Token token = (Token) joinPoint.getArgs()[0];
        UserId userId = (UserId) joinPoint.getArgs()[1];

        AuthenticationToken innerToken = tokenRepository.findByKey(token.key);

        if (innerToken == null || innerToken.getUserId() != userId.id) {
            throw new AuthenticationException("Invalid key.");
        }
        if (innerToken.getExpirationTime().compareTo(LocalDateTime.now()) < 1) {
            throw new AuthenticationException("Token has been expired.");
        }

        joinPoint.proceed();
    }
}
