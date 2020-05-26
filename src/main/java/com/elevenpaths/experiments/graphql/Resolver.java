package com.elevenpaths.experiments.graphql;

import graphql.schema.DataFetcher;

public interface Resolver {
    public String getTypeName();
    public String getFieldName();
    public DataFetcher<?> getResolver();
}
