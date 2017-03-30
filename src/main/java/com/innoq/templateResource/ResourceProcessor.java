package com.innoq.templateResource;

import com.innoq.templateResource.internal.Context;
import com.innoq.templateResource.internal.ReadingState;

import java.util.Map;
import java.util.stream.Stream;

/**
 *
 */
public final class ResourceProcessor {

    private int zeilenNummer;

    private Context<Query> ctx;
    private ReadingState currentState;

    public ResourceProcessor(Map<String, Query> resultCollector) {
        this.ctx = new Context<>(new Query(), resultCollector);
    }


    public Map<String, Query> process(Stream<String> lines) {
        zeilenNummer = 1;
        currentState = ReadingState.INITIAL;

        lines.forEach(this::processRow);

        return ctx.collectResults();
    }


    private void processRow(String line) {
        currentState = currentState.process(line, ctx, zeilenNummer);
        zeilenNummer++;
    }
}
