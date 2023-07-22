package com.alesgaroth.zuv;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

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
}
