package com.teamdev.webapp;

import com.teamdev.business.impl.application.ApplicationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextProvider {
    private static ContextProvider ourInstance = new ContextProvider();

    private AnnotationConfigApplicationContext context;

    public static ContextProvider getInstance() {
        return ourInstance;
    }

    private ContextProvider() {
        context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }
}
