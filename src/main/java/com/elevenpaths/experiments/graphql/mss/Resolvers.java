package com.elevenpaths.experiments.graphql.mss;

import com.elevenpaths.experiments.graphql.core.Resolver;
import com.elevenpaths.experiments.graphql.mss.Model.*;
import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherResult;
import graphql.execution.ExecutionId;
import graphql.schema.DataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.concurrent.Future;

import static graphql.execution.DataFetcherResult.newResult;

@Slf4j
@Component("TicketResolver")
public final class Resolvers implements Resolver {

    @Override
    public String getTypeName() {
        return "Query";
    }

    @Override
    public String getFieldName() {
        return "search_tickets";
    }

    @Override
    public DataFetcher<Future<DataFetcherResult<TicketSearchResult>>> getResolver() {
        return env -> {

            ExecutionId id = env.getExecutionId();

            String context = env.getArgument("context");
            ArrayList<String> fields = env.getArgument("fields");

            env.getSelectionSet().getFields().forEach(f -> {
                log.info("ExecutionId [{}] Field [{}]", id, f.getName());
            });

            env.getArguments().entrySet().forEach(entry -> {
                log.info("ExecutionId [{}] Argument [{}][{} => {}]", id, entry.getKey(), entry.getValue().getClass().getCanonicalName(),entry.getValue());
            });

            DataFetcherResult.Builder<TicketSearchResult> r = DataFetcherResult.newResult();

            TicketSearchResult sr = new TicketSearchResult();

            MetaResult meta = new MetaResult();
            meta.setTook(100);
            meta.setHits(666);
            sr.setMeta(meta);
            TicketResult tr = new TicketResult();
            tr.setCategory("CAT-1");
            tr.setDescription("This is a foo description");
            tr.setTicketId("ticket-1");
            tr.setPriority(1);
            sr.getItems().add(tr);

            r = r.data(sr);

            r.error(
                    GraphqlErrorBuilder.newError(env)
                            .errorType(ErrorType.DataFetchingException)
                            .message("ExecutionId [%s] A custom error simulation.", id)
                            .build()
            );

            return Mono.just(r.build()).toFuture();
        };
    }
}
