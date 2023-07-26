package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;

public class ZNodeTest {
    @Test
    void cansetInput() {
        ZNode<Integer> n = new ZNode<>(1, 0);
        ZNode<Integer> m = new ZConstant<>(1);
        n.setInput(m.output(0), 0);
        
    }
}
