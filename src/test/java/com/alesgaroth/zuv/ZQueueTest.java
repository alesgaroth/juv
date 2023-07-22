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

    @Test
    void canPutTwothingsInQueue() {
        ZNode<Integer> n = new ZNode<Integer>();
        ZNode<Integer> m = new ZNode<Integer>();
        q.doLater(n);
        q.doLater(m);
        assertSame(n, q.next(), "First in queue is first out");
        assertSame(m, q.next(), "Second in queue is second out");
    }

    @Test
    void canPutBack() {
       ZNode<Integer> n = new ZNode<Integer>();
       ZNode<Integer> m = new ZNode<Integer>();
       q.doLater(n);
       q.doLater(m);
       assertSame(n, q.next(), "First in queue is first out");
       q.prepend(n);
       assertSame(n, q.next(), "First put back is first out");
    }

    @Test
    void canPrepend() {
       ZNode<Integer> n = new ZNode<Integer>();
       ZNode<Integer> m = new ZNode<Integer>();
       ZNode<Integer> i = new ZNode<Integer>();
       q.doLater(n);
       q.doLater(m);
       assertSame(n, q.next(), "First in queue is first out");
       q.prepend(n);
       q.prepend(i);
       assertSame(i, q.next(), "Second put back is first out");
       assertSame(n, q.next(), "First put back is second out");
       assertSame(m, q.next(), "last added to end is last out");
    }

}
