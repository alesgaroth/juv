package com.alesgaroth.zuv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

public class ZQueueTest {
    ZQueue q;
    @BeforeEach
    void before() {
         q = new ZQueue();
    }
    
    @Test
    void canPutOnQueue() {
        ZNode n = new ZNode(0, 0);
        q.enqueue(n);
        assertSame(n, q.next(), "Empty Queue push/pop should return same.");
    }

    @Test
    void canPutTwothingsInQueue() {
        ZNode n = new ZNode(0, 0);
        ZNode m = new ZNode(0, 0);
        q.enqueue(n);
        q.enqueue(m);
        assertSame(n, q.next(), "First in queue is first out");
        assertSame(m, q.next(), "Second in queue is second out");
    }

    @Test
    void canPutBack() {
       ZNode n = new ZNode(0, 0);
       ZNode m = new ZNode(0, 0);
       q.enqueue(n);
       q.enqueue(m);
       assertSame(n, q.next(), "First in queue is first out");
       q.prepend(n);
       assertSame(n, q.next(), "First put back is first out");
    }

    @Test
    void canPrepend() {
       ZNode n = new ZNode(0, 0);
       ZNode m = new ZNode(0, 0);
       ZNode i = new ZNode(0, 0);
       q.enqueue(n);
       q.enqueue(m);
       assertSame(n, q.next(), "First in queue is first out");
       q.prepend(n);
       q.prepend(i);
       assertSame(i, q.next(), "Second put back is first out");
       assertSame(n, q.next(), "First put back is second out");
       assertSame(m, q.next(), "last added to end is last out");
    }

    @Test
    void canLoop() {
      ZVar var1 = new ZVar(3);
      ZNode gn  = ZPassThruTest.createPassThru(var1.output(0), q);
      q.enqueue(gn);
      var1.set((Integer)8, q);
      q.runTillEmpty();
      assertEquals(8, gn.output(0).fetch(q));
    }

    @Test
    void canSpecifyAWaitTime() { // for timeouts
      ZVar var1 = new ZVar(3);
      ZNode gn = ZPassThruTest.createPassThru(var1.output(0), q);
      q.enqueueIn(gn, Duration.ofMillis(2L));
      q.next();
    }

}
