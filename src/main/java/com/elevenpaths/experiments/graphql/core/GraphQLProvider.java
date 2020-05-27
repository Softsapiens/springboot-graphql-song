package com.elevenpaths.experiments.graphql.core;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.scalars.regex.RegexScalar;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
@Slf4j
public class GraphQLProvider {

    private GraphQL graphQL;

    @Autowired
    List<Resolver> resolvers;

    @PostConstruct
    public void init() throws IOException {

        URL url = Resources.getResource("schema.graphqls");

        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();

        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {

        RuntimeWiring.Builder rt = RuntimeWiring.newRuntimeWiring();

        resolvers.forEach(resolver -> {
            log.info("Processing resolver [{}][{}]", resolver.getTypeName(), resolver.getFieldName());

            rt
                    .type(newTypeWiring(resolver.getTypeName())
                    .dataFetcher(resolver.getFieldName(), resolver.getResolver()));
        });

        // Scalars
        rt.scalar(ExtendedScalars.DateTime);
        rt.scalar(ExtendedScalars.Json);
        rt.scalar(ExtendedScalars.Object);

        RegexScalar emailScalar = ExtendedScalars.newRegexScalar("Email")
                .addPattern(Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$"))
                .build();

        rt.scalar(emailScalar);

        return rt.build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

}
