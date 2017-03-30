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
