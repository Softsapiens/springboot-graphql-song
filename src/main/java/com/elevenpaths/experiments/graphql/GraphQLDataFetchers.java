package com.elevenpaths.experiments.graphql;

import com.elevenpaths.experiments.neith.Model.*;
import graphql.schema.DataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.concurrent.Future;

@Component
@Slf4j
public class GraphQLDataFetchers {

    public DataFetcher<Future<Book>> getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");

            dataFetchingEnvironment.getSelectionSet().getFields().forEach(f -> {
                log.info("Field [{}]", f.getName());
            });

            Author author = new Author();
            author.setId("author-1");
            author.setFirstName("John");
            author.setLastName("Foo");
            Book book = new Book();
            book.setId(bookId);
            book.setName("GraphQL song");
            book.setPageCount(666);
            book.setAuthor(author);

            return Mono.just(book).toFuture();
        };
    }

    public DataFetcher<Future<NSearchResult>> getSearchDataFetcher() {
        return dataFetchingEnvironment -> {

            String context = dataFetchingEnvironment.getArgument("context");
            ArrayList<String> fields = dataFetchingEnvironment.getArgument("fields");

            dataFetchingEnvironment.getSelectionSet().getFields().forEach(f -> {
                log.info("Field [{}]", f.getName());
            });

            dataFetchingEnvironment.getArguments().entrySet().forEach(entry -> {
                log.info("Field [{}][{} => {}]", entry.getKey(), entry.getValue().getClass().getCanonicalName(),entry.getValue());
            });

            NSearchResult ns = new NSearchResult();
            ns.setTook(100);

            return Mono.just(ns).toFuture();
        };
    }
}
