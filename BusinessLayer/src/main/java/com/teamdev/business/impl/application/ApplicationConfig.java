package com.teamdev.business.impl.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;

@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.teamdev.business.impl","com.teamdev.persistence"})
public class ApplicationConfig {

    @PostConstruct
    public void doSomething() {
        System.out.println("Spring initialized");
    }
}
