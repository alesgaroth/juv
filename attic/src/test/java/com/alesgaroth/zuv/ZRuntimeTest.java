package com.alesgaroth.zuv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZRuntimeTest {

    @Test void canFetchQueue() {
        ZRuntime rt = new ZRuntime();
        rt.getQueue();
        rt.parse("1");
    }

    @Test void canParseStringConstant() {
        ZRuntime rt = new ZRuntime();
        ZNode n = rt.parse("\"1\"");
        Assertions.assertInstanceOf(ZConstant.class, n, "Should be a ZConstant with value 1");
        Assertions.assertEquals("\"1\"", n.output(0).fetch(rt.getQueue()));
    }

    @Test void canParseIntConstant() {
        ZRuntime rt = new ZRuntime();
        ZNode n = rt.parse("1");
        Assertions.assertInstanceOf(ZConstant.class, n, "Should be a ZConstant with value 1");
        Object y =  n.output(0).fetch(rt.getQueue());
        Assertions.assertInstanceOf(Integer.class, y, "Should be an Integer");
        Assertions.assertEquals(1, y, "Should be 1");
    }

    @Test void canEval() {
        String s = ZRuntime.staticEval("(+ 1 1)");
        Assertions.assertEquals("2", s);
    }
    
}
