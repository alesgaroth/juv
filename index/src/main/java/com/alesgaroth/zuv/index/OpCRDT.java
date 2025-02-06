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
    queueIt(new Add(e, replica + ":" + thisc, s));
  }
  private void queueIt(Effect effect) {
    queue.add(effect);
    effect.apply();
  }
  public void remove(E e) {
    Set<String> s = m.get(e);
    if (s != null) {
      s = new HashSet<>(s);
    }
    queueIt(new Remove(e, s));
  }

  public void setListener(CRDTListener<E> l) {
  }

  public class Add implements Effect {
    E e; String d; Set<String> r;
    Add(E e, String d, Set<String> r) {
      this.e = e;
      this.d = d;
      this.r = r;
    }
    public void apply() {

      Set<String> s;
      synchronized (m) {
        s = m.computeIfAbsent(e, e -> new HashSet<String>());
      }
      if (r != null) {
        s.removeAll(r);
      }
      synchronized (m) {
        s = m.computeIfAbsent(e, e -> new HashSet<String>());
        s.add(d);
      }
    }
  }
  public class Remove implements Effect {
    E e; Set<String> r;
    Remove(E e, Set<String> r) {
      this.e = e;
      this.r = r;
    }
    public void apply() {
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
    }
  }

  static public interface Effect {
    void apply();
  }

}
