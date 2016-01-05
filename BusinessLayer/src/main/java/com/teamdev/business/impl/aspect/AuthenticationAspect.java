package com.teamdev.business.impl.aspect;

import com.teamdev.business.AuthenticationService;
import com.teamdev.business.impl.dto.Token;
import com.teamdev.business.impl.dto.UserId;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationAspect {

    @Autowired
    private AuthenticationService authenticationService;

    @Pointcut("execution (* com.teamdev.business.*.*" +
            "(com.teamdev.business.impl.dto.Token,com.teamdev.business.impl.dto.UserId,..)) &&" +
            " !execution(* validation(..))")
    private void authPointcut() {
    }

    @Around("authPointcut()")
    public void authentication(ProceedingJoinPoint joinPoint) throws Throwable {

        Token token = (Token) joinPoint.getArgs()[0];
        UserId userId = (UserId) joinPoint.getArgs()[1];

        authenticationService.validation(token, userId);

        joinPoint.proceed();
    }
}
