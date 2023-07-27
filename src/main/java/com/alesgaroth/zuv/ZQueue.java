package com.alesgaroth.zuv;

import java.util.Deque;
import java.util.LinkedList;

import org.joda.time.Duration;

public class ZQueue {

    Deque<ZNode<?>> deque = new LinkedList<>();

    public <T> void enqueue(ZNode<T> zNode) {
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

    private static class ZNullQueue extends ZQueue {
      public <T> void enqueue(ZNode<T> zNode) { }
      public ZNode<?> next() { return null; }
      public void prepend(ZNode<?> n) { }
      public void runTillEmpty() { }
    }

    public void enqueueIn(ZNode<Integer> gn, Duration duration) {
    }
}
