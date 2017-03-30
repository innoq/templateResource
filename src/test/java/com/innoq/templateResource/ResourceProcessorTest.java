package com.innoq.templateResource;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Created by Phillip on 30.03.2017.
 */
public class ResourceProcessorTest extends TestCase {

    public void testCreateQuery() throws IOException {

        HashMap<String, Query> queries = new HashMap<>();
        new ResourceProcessor(queries).process(
            Stream.of(
                    "--- name: the-top-secret-query",
                    "--- ",
                    "--- the description of this query is important!",
                    "--- ",
                    "",
                    "select elephant from africa;"
            ));

        assertTrue( queries.containsKey("the-top-secret-query") );
        assertEquals( "select elephant from africa;", queries.get("the-top-secret-query").getStatement() );
    }
}
