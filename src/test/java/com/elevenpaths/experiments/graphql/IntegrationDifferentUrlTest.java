package com.elevenpaths.experiments.graphql;

import graphql.ExecutionInput;
import graphql.ExecutionResultImpl;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "graphql.url=otherUrl")
@RunWith(SpringRunner.class)
public class IntegrationDifferentUrlTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    GraphQL graphql;

    @Test
    public void endpointIsAvailableWithDifferentUrl() {
        String query = "{foo}";

        ExecutionResultImpl executionResult = ExecutionResultImpl.newExecutionResult()
                .data("bar")
                .build();
        CompletableFuture cf = CompletableFuture.completedFuture(executionResult);
        ArgumentCaptor<ExecutionInput> captor = ArgumentCaptor.forClass(ExecutionInput.class);
        Mockito.when(graphql.executeAsync(captor.capture())).thenReturn(cf);

        this.webClient.get().uri("/otherUrl?query={query}", query).exchange().expectStatus().isOk()
                .expectBody(String.class).isEqualTo("{\"data\":\"bar\"}");

        assertThat(captor.getValue().getQuery(), is(query));
    }

}
