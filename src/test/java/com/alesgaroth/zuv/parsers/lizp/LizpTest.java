package com.alesgaroth.zuv.parsers.lizp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.alesgaroth.zuv.ZConstant;
import com.alesgaroth.zuv.ZNode;
import com.alesgaroth.zuv.ZQueue;
import com.alesgaroth.zuv.parsers.ZParser;

public class LizpTest {
    @Test void canParseConstant() {
        ZParser prsr = new Lizp();
        ZNode n = prsr.parse("1");
        ZNode expected = new ZConstant(1);
        assertEquals(expected, n);
    }
}
