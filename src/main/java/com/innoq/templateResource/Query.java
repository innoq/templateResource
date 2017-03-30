package com.innoq.templateResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This domain class encapsulates a query,
 * its params, its description, and the name
 *
 * @since 2015-06-02
 */
public class Query {

    private String name;
    private String description;
    private Optional<String> statement;
    private List<String> linesOfStatement;

    private List<String> params;

    public Query() {
        this.description = "";
        this.statement = Optional.empty();
        this.params = new ArrayList<>();
        this.linesOfStatement = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void appendToDescription(String description) {
        this.description += " " + description;
    }

    public String getStatement() {
        if ( !statement.isPresent() ) {
            statement = linesOfStatement.stream().reduce( (a,b) -> a + " " + b );
        }
        return statement.orElseGet( String::new );
    }
    public void appendToStatement(String statement) {
        this.linesOfStatement.add(statement);
    }

    public List<String> getParams() { return params; }
    public void addParam(String paramName) { params.add(paramName); }


    @Override
    public String toString() {
        if ( !params.isEmpty() ) {
            return String.format( "Query[%s].withParams(%s)", name, params );
        } else {
            return String.format("Query[%s]", name );
        }
    }
}
