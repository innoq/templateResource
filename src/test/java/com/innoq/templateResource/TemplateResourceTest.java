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

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Phillip on 23.03.2017.
 */
public class TemplateResourceTest extends TestCase {

    TemplateResource queryResource;

    @Override
    public void setUp() throws IOException {

        if ( queryResource == null ) {
            log(new File("./").getAbsolutePath());
            queryResource = TemplateResource.from("./res/test/test_queries.sql");
            log( queryResource );
        }
    }


    public void testRead() {

        assertFalse( queryResource.isEmpty() );
        assertTrue( queryResource.containsKey( "find all -- no params" ));
    }

    public void testGetStatement() {
        assertArrayEquals(
                new String[] {"select", "*", "from","people;"},
                queryResource.getQueryAsString( "find all -- no params").split("\\s+" ) );
    }

    public void testGetParam() {

        assertEquals( 1, queryResource.get( "find person by id" ).getParams().size() );
    }

    public void testGetManyParams() {
        assertEquals( 2, queryResource.get( "find people by name and city" ).getParams().size() );
    }


    public void testPrintQuery() {
        final Query query = queryResource.get("find person by id");
        System.out.println(query);
        System.out.println( query.getDescription() );
    }


    /**
     * private method that proxies log-facility (here: System.out)
     * */
    private void log(Object o) {
        if ( Boolean.valueOf( System.getProperty( "debug", "false") ) ) {
            System.out.println( o );
        }
    }
}
