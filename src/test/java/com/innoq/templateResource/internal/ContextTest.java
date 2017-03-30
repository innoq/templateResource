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
