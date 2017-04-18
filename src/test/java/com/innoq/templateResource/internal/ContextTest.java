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
import junit.framework.TestCase;

import java.util.HashMap;

/**
 * Created by Phillip on 23.03.2017.
 */
public class ContextTest extends TestCase {

    public void testInitContext() {
        Context<Query> ctx = new Context<>(new Query(), new HashMap<>());

        assertSame( ctx.current, ctx.collectResults().entrySet().iterator().next().getValue() );
        assertNull( ctx.current.getName() );
        assertTrue( ctx.current.getDescription().isEmpty() );
        assertTrue( ctx.current.getStatement().isEmpty() );
    }

    public void testPush() {
        Context<Query> ctx = new Context<>(new Query(), new HashMap<>());

        ctx.current.setName("ABC");

        ctx.push( new Query() );

        assertNull( ctx.current.getName() );
        assertTrue( ctx.collectResults().containsKey( "ABC" ) );
        assertEquals( "ABC", ctx.collectResults().get("ABC").getName() );
    }
}
