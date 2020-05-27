package com.elevenpaths.experiments.graphql.mss;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Model {
    @Data
    public static class TicketSearchResult {

        public TicketSearchResult() {
            this.items = new ArrayList<TicketResult>();
        }

        @Getter
        @Setter
        MetaResult meta;

        @Getter
        ArrayList<TicketResult> items;
    }

    @Data
    public static class TicketResult {
        @Getter
        @Setter
        String ticketId;

        @Getter
        @Setter
        String category;

        @Getter
        @Setter
        String description;

        @Getter
        @Setter
        Integer priority;
    }

    @Data
    public static class MetaResult {
        @Getter
        @Setter
        Integer took;

        @Getter
        @Setter
        Integer hits;
    }
}
