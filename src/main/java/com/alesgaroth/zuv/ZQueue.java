package com.alesgaroth.zuv;

import java.util.Deque;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ZQueue {

    Deque<ZNode> deque = new LinkedList<>();
    TreeMap<Instant, Set<ZNode>> futureQueue = new TreeMap<>();

    public void enqueue(ZNode zNode) {
        deque.add(zNode);
    }

    public ZNode next() {
        nextFuture();
        return deque.remove();
    }

    public void prepend(ZNode n) {
        deque.addFirst(n);
    }

    public void runTillEmpty() {
        while(!deque.isEmpty()) {
            ZNode n = next();
            n.execute(this);
        }
    }

    public static ZQueue nullQueue = new ZNullQueue();

    private static class ZNullQueue extends ZQueue {
      public void enqueue(ZNode zNode) { }
      public ZNode next() { return null; }
      public void prepend(ZNode n) { }
      public void runTillEmpty() { }
    }

    public void enqueueIn(ZNode gn, Duration duration) {
        Instant inst = Instant.now().plus(duration);
        setAtTime(gn, inst);

    }

    private void setAtTime(ZNode gn, Instant inst) {
        Set<ZNode> listAtTime = futureQueue.get(inst);
        if (listAtTime == null) {
            listAtTime = new HashSet<>();
        }
        listAtTime.add(gn);
        futureQueue.put(inst, listAtTime);
    }

    private void nextFuture() {
        while (!futureQueue.isEmpty() && futureQueue.firstEntry().getKey().isBefore(Instant.now())) {
            deque.addAll(futureQueue.firstEntry().getValue());
        }
        if (deque.isEmpty() && !futureQueue.isEmpty()) {
            Instant inst = futureQueue.firstEntry().getKey();
            long howlong = Instant.now().until(inst, ChronoUnit.MILLIS);
            try {
                Thread.sleep(howlong);
            } catch (InterruptedException ie) {}
            deque.addAll(futureQueue.firstEntry().getValue());
        }
    }
}
