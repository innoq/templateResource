/*
 * Copyright 2017 innoQ Deutschland GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
