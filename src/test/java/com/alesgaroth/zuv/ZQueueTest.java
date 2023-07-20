package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ZQueueTest {
    ZQueue q;
    @BeforeEach
    void before() {
         q = new ZQueue();
    }
    
    @Test
    void canPutOnQueue() {
        ZNode<Integer> n = new ZNode<Integer>();
        q.doLater(n);
        assertSame(n, q.next(), "Empty Queue push/pop should return same.");
    }
}
