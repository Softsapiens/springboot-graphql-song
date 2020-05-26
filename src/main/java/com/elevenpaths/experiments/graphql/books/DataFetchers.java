package com.elevenpaths.experiments.graphql.books;

import com.elevenpaths.experiments.books.Model.*;
import com.elevenpaths.experiments.graphql.Resolver;
import graphql.schema.DataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.Future;

@Slf4j
@Component("BookResolver")
public class DataFetchers implements Resolver {

    @Override
    public String getTypeName() {
        return "Query";
    }

    @Override
    public String getFieldName() {
        return "bookById";
    }

    @Override
    public DataFetcher<Future<Book>> getResolver() {
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
}
