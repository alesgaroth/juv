package com.alesgaroth.zuv;

public class ZQueue {

    ZNode<?> stack;

    public <T> void doLater(ZNode<T> zNode) {
        stack = zNode;
    }

    public ZNode<?> next() {
        return stack;
    }

}
