package com.elevenpaths.experiments.spring.learn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
//@Scope(value = "prototype")
public class StartUpTrazerBean implements InitializingBean {

    // https://www.baeldung.com/running-setup-logic-on-startup-in-spring


    public StartUpTrazerBean() {
        log.info("Constructor");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("InitializingBean");
    }

    @PostConstruct
    public void postConstruct() {
        log.info("PostConstruct");
    }

    public void init() {
        log.info("init-method");
    }
}
