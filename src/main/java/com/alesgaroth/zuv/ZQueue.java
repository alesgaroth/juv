package com.alesgaroth.zuv;

import java.util.Deque;
import java.util.LinkedList;

public class ZQueue {

    Deque<ZNode<?>> deque = new LinkedList<>();

    public <T> void doLater(ZNode<T> zNode) {
        deque.add(zNode);
    }

    public ZNode<?> next() {
        return deque.remove();
    }

    public void prepend(ZNode<?> n) {
        deque.addFirst(n);
    }

    public void runTillEmpty() {
        while(!deque.isEmpty()) {
            ZNode<?> n = next();
            n.execute(this);
        }
    }

    public static ZQueue nullQueue = new ZNullQueue();

    public static class ZNullQueue extends ZQueue {
      public <T> void doLater(ZNode<T> zNode) { }
      public ZNode<?> next() { return null; }
      public void prepend(ZNode<?> n) { }
      public void runTillEmpty() { }
    }
}
