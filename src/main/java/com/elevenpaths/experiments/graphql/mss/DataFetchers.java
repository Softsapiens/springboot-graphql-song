package com.elevenpaths.experiments.graphql.mss;

import com.elevenpaths.experiments.graphql.core.Resolver;
import com.elevenpaths.experiments.graphql.mss.Model.*;
import graphql.schema.DataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.concurrent.Future;

@Slf4j
@Component("TicketResolver")
public final class DataFetchers implements Resolver {

    @Override
    public String getTypeName() {
        return "Query";
    }

    @Override
    public String getFieldName() {
        return "search_tickets";
    }

    @Override
    public DataFetcher<Future<TicketSearchResult>> getResolver() {
        return dataFetchingEnvironment -> {

            String context = dataFetchingEnvironment.getArgument("context");
            ArrayList<String> fields = dataFetchingEnvironment.getArgument("fields");

            dataFetchingEnvironment.getSelectionSet().getFields().forEach(f -> {
                log.info("Field [{}]", f.getName());
            });

            dataFetchingEnvironment.getArguments().entrySet().forEach(entry -> {
                log.info("Field [{}][{} => {}]", entry.getKey(), entry.getValue().getClass().getCanonicalName(),entry.getValue());
            });

            TicketSearchResult sr = new TicketSearchResult();
            sr.setTook(100);
            TicketResult tr = new TicketResult();
            tr.setCategory("CAT-1");
            tr.setDescription("This is a foo description");
            tr.setTicketId("ticket-1");
            tr.setPriority(1);
            sr.getItems().add(tr);

            return Mono.just(sr).toFuture();
        };
    }
}
