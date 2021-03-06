package com.innoq.templateResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * <em>TemplateResource</em> proxies external files of queries and
 * provides access to them via a HashMap.
 *
 * TemplateResources contain an arbitrary number of queries in a
 * the format:
 *
 * <pre>
 *     --- name: blabla
 *     ---
 *     --- description
 *     --- params:
 *
 *     select the, desired, query
 *     from if_you_want_across_multiple_lines
 * </pre>
 *
 * <p>
 * TemplateResource doesn't assume a specific query language.
 * It's contract expects the definition of a query consisting of
 * a header and the query itself.
 * </p>
 *
 * <p>The header is marked with end-of-line-comments.</p>
 *
 * <p>
 * The first line has to declare the name of the query.
 * Followed by an empty line the header might contain a
 * description of the query.
 * </p>
 *
 * <p>
 * If clients don't want to depend on the specific
 * TemplateResource, it can be proxied and used as a
 * simple HashMap.
 * </p>
 *
 * @since 2015-06-02
 *
 * @see Query
 */
public class TemplateResource extends HashMap<String, Query> {

    public TemplateResource() { }

    public static TemplateResource from( final File file ) throws IOException {
        TemplateResource templateResource = new TemplateResource();
        ResourceProcessor processor = new ResourceProcessor(templateResource);

        processor.process( Files.lines( file.toPath() ) );
        return templateResource;
    }


    public static TemplateResource from( final String filename ) throws IOException {
        return from( new File( filename ) );
    }


    public String getQueryAsString(String queryName) {

        return get(queryName).getStatement();
    }

}
