package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;

public class ZNodeTest {
    @Test
    void cansetInput() {
        ZNode n = new ZNode(1, 0);
        ZNode m = new ZConstant(1);
        n.setInput(m.output(0), 0);
        
    }
}
