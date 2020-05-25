package com.elevenpaths.experiments.graphql;

import graphql.GraphQL;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationTestConfig {

    @MockBean
    public GraphQL graphQL;
}