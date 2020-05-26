package com.elevenpaths.experiments.graphql;

import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.elevenpaths.experiments")
@Slf4j
public class SongApp {

    @Autowired
    GraphQL graphql;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SongApp.class);
        app.setBannerMode(Banner.Mode.OFF);

        app.run(args);
    }
}