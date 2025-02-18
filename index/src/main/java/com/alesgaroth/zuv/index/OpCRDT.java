package com.alesgaroth.zuv.index;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class OpCRDT<E extends Serializable> implements CRDT<E> {
  Map<E, Set<String>> m = new HashMap<>();
  int c = 1;
  String replica;
  Queue<Effect> queue;
  CRDTListener<E> listener ;
  OpCRDT<E> other;

  public OpCRDT(String replica, Queue<Effect> queue) {
    this.queue = queue;
    this.replica = replica;
  }

  public Set<E> elements() {
    return new HashSet<>(m.keySet());
  }
  public boolean contains(E e) {
    return m.containsKey(e);
  }
  public void add(E e) {
    long thisc;
    synchronized(this) {
      thisc = c;
      c += 1;
    }
    Set<String> s = m.get(e);
    if (s != null) {
      s = new HashSet<>(s);
    }
    queueIt(new Addition<E>(e, replica + ":" + thisc, s));
  }
  private void queueIt(Effect effect) {
    queue.add(effect);
    effect.apply(this);
    if (other != null) {
      other.changed(effect);
    }
  }
  public void remove(E e) {
    Set<String> s = m.get(e);
    if (s != null) {
      s = new HashSet<>(s);
    }
    queueIt(new Removal(e, s));
  }

  public void setListener(CRDTListener<E> l) {
    this.listener = l;
  }

  private void added(E e, String d, Set<String> r) {
    Set<String> s;
    synchronized (m) {
      s = m.computeIfAbsent(e, y -> new HashSet<String>());
    }
    if (r != null) {
      s.removeAll(r);
    }
    synchronized (m) {
      s = m.computeIfAbsent(e, y -> new HashSet<String>());
      s.add(d);
    }
    if (listener != null) {
      listener.added(e);
    }
  }

  private void removed(E e, Set<String> r) {
    if (r != null) {
      // there's a race condition here...
      Set<String>s = m.get(e);
      if (s != null) {
        s.removeAll(r);
        synchronized(m) {
          if (s.isEmpty()) {
            m.remove(e);
          }
        }
      }
    }
    if (listener != null) {
      listener.removed(e);
    }
  }

  public void replicateTo(OpCRDT other) {
    this.other = other;
  }

  public void changed(Effect eff) {
    eff.apply(this);
  }

  private static class Addition<E extends Serializable> implements Effect {
    E e; String d; Set<String> r;
    Addition(E e, String d, Set<String> r) {
      this.e = e;
      this.d = d;
      this.r = r;
    }
    public void apply(OpCRDT rcvr) {
      rcvr.added(e, d, r);
    }
  }

  private static class Removal<E extends Serializable> implements Effect {
    E e; Set<String> r;
    Removal(E e, Set<String> r) {
      this.e = e;
      this.r = r;
    }
    public void apply(OpCRDT rcvr)  {
      rcvr.removed(e, r);
    }
  }

  static public interface Effect {
    void apply(OpCRDT rcvr);
  }

}
