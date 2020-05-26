package com.elevenpaths.experiments.neith;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class Model {

    @Data
    public static class NSearchResult {
        @Getter
        @Setter
        Integer took;
    }

    @Data
    public static class Author {
        @Getter
        @Setter
        String id;

        @Getter
        @Setter
        String firstName;

        @Getter
        @Setter
        String lastName;
    }

    @Data
    public static class Book {
        @Getter
        @Setter
        String id;

        @Getter
        @Setter
        String name;

        @Getter
        @Setter
        Integer pageCount;

        @Getter
        @Setter
        Author author;
    }
}
