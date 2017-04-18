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
