package com.alesgaroth.zuv;

import java.util.Deque;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ZQueue {

    Deque<Executable> deque = new LinkedList<>();
    TreeMap<Instant, Set<Executable>> futureQueue = new TreeMap<>();

    ZGraphNode root;

    public void setRoot(ZGraphNode root) {
      this.root = root;
    }

    public ZGraphNode getRoot() {
      return root;
    }

    public void enqueue(Executable zNode) {
        deque.add(zNode);
    }

    public Executable next() { // public for testing
        nextFuture();
        return deque.remove();
    }

    public void prepend(Executable n) {
        deque.addFirst(n);
    }

    public void runTillEmpty() {
        while(!deque.isEmpty()) {
            Executable n = next();
            n.execute(this);
        }
    }

    public static ZQueue nullQueue = new ZNullQueue();

    private static class ZNullQueue extends ZQueue {
      public void enqueue(Executable zNode) { }
      public Executable next() { return null; }
      public void prepend(Executable n) { }
      public void runTillEmpty() { }
    }

    public void enqueueIn(Executable gn, Duration duration) {
        Instant inst = Instant.now().plus(duration);
        setAtTime(gn, inst);

    }

    private void setAtTime(Executable gn, Instant inst) {
        Set<Executable> listAtTime = futureQueue.get(inst);
        if (listAtTime == null) {
            listAtTime = new HashSet<>();
        }
        listAtTime.add(gn);
        futureQueue.put(inst, listAtTime);
    }

    private void nextFuture() {
        while (!futureQueue.isEmpty()) {
            Instant inst = futureQueue.firstEntry().getKey();
            if (inst.isAfter(Instant.now())) {
                long howlong = Instant.now().until(inst, ChronoUnit.MILLIS);
                if (howlong > 0) {
                    try {
                        Thread.sleep(howlong); // At this point we should wait or network IO etc.
                    } catch (InterruptedException ie) {}
                }
            }
            deque.addAll(futureQueue.firstEntry().getValue());
            futureQueue.remove(inst);
        }
    }

    public interface Executable {
      void execute(ZQueue q);
    }
}
