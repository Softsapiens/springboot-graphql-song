package com.elevenpaths.experiments.graphql;

import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class SongApp {

    @Autowired
    GraphQL graphql;

    public static void main(String[] args) {
        SpringApplication.run(SongApp.class, args);
    }
}