package com.alesgaroth.zuv.parsers.lizp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import com.alesgaroth.zuv.ZAdd;
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
        testParsesTo("\"string\"", new ZConstant("string"));
    }

    @Disabled
    @Test void canParseAddition() {
        ZNode adder = new ZAdd();
        ZNode one = new ZConstant(1);
        adder.setInput(one.output(0), 0);
        adder.setInput(one.output(0), 1);
        testParsesTo("(+ 1 1)", adder);
    }
}
