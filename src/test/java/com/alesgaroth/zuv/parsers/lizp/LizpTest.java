package com.alesgaroth.zuv.parsers.lizp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.alesgaroth.zuv.ZConstant;
import com.alesgaroth.zuv.ZNode;
import com.alesgaroth.zuv.ZQueue;
import com.alesgaroth.zuv.parsers.ZParser;

public class LizpTest {

    void testParsesTo(String input, Object expected) {
        ZParser prsr = new Lizp();
        ZNode n = prsr.parse(input);
        assertEquals(expected, n);
    }


    @Test void canParseIntConstant() {
        testParsesTo("1", new ZConstant(1));
    }

    @Test void canParseStringConstant() {
        ZParser prsr = new Lizp();
        ZNode n = prsr.parse("\"string\"");
        ZNode expected = new ZConstant("string");
        assertEquals(expected, n);
    }
}
