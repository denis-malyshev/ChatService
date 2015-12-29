package com.teamdev.business.impl.model;

import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;


@ComponentScan(basePackages = {"com.teamdev.business.impl","com.teamdev.persistence"})
public class ApplicationConfig {

    @PostConstruct
    public void doSomething() {
        System.out.println("Spring initialized");
    }
}
