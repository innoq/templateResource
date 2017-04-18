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
package com.innoq.templateResource.internal;

import com.innoq.templateResource.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class <em>Content</em> provides the memory of a the processing
 * of a TemplateResource-File.
 *
 * Usually, this class is parameterized with {@link Query} as the kind of
 * thing that should be retrieved from a file.
 */
public class Context<T extends Query> {

    T current;
    private Map<String, T> result;
    private List<T> acc = new ArrayList<>();

    /**
     * @param currentObject - the first object that's going to be enhanced while reading the input.
     * @param result - the map that is modified during the processing of the input file.
     * */
    public Context(T currentObject, Map<String, T> result) {
        this.result = result;
        push( currentObject );
    }

    void push(T obj) {
        acc.add( obj );
        current = obj;
    }

    public Map<String, T> collectResults() {

        acc.forEach(v -> result.put( v.getName(), v) );
        return result;
    }
}
