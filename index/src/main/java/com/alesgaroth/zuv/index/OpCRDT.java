package com.alesgaroth.zuv.index;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OpCRDT implements Replica, CRDT {
  Map<String, Set<String>> m = new HashMap<>();
  int c = 1;
  String replica;
  CRDTListener listener ;
  Replica other;

  public OpCRDT(String replica) {
    this.replica = replica;
  }

  public Set<String> elements() {
    return Collections.unmodifiableSet(new HashSet<>(m.keySet()));
  }
  public boolean contains(String e) {
    return m.containsKey(e);
  }
  public void add(String e) {
    long thisc;
    synchronized(this) {
      thisc = c;
      c += 1;
    }
    Set<String> s = m.get(e);
    if (s != null) {
      s = new HashSet<>(s);
    }
    queueIt(new Addition(e, replica + ":" + thisc, s));
  }
  public void remove(String e) {
    Set<String> s = m.get(e);
    if (s != null) {
      s = new HashSet<>(s);
    }
    queueIt(new Removal(e, s));
  }

  public void setListener(CRDTListener l) {
    this.listener = l;
  }

  private void queueIt(Effect effect) {
    effect.apply(this);
    if (other != null) {
      other.changed(effect);
    }
  }

  public void replicateTo(Replica other) {
    this.other = other;
  }

  public void changed(Effect eff) {
    eff.apply(this);
  }

  private void added(String e, String d, Set<String> r) {
    synchronized (m) {
      Set<String> s;
      if (m.containsKey(e)) {
        s = m.get(e);
      } else {
        s = new HashSet<String>();
      }
      if (r != null) {
        s.removeAll(r);
      }
      s.add(d);
      m.put(e, s);
    }
    if (listener != null) {
      listener.added(e);
    }
  }

  private void removed(String e, Set<String> r) {
    if (r != null) {
      Set<String>s = m.get(e);
      if (s != null) {
        s.removeAll(r);
        synchronized(m) {
          s = m.get(e);
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

  public static class Addition implements Effect {
    String e; String d; Set<String> r;
    public Addition(String e, String d, Set<String> r) {
      this.e = e;
      this.d = d;
      this.r = r;
    }
    public void apply(Object rcvr) {
      if (rcvr instanceof OpCRDT op) {
        op.added(e, d, r);
      }
    }
    public String toString() {
      return "added " + e + " " + d + ((r == null)?"":(" " + String.join(" ", r))); 
    }
  }

  public static class Removal implements Effect {
    String e; Set<String> r;
    public Removal(String e, Set<String> r) {
      this.e = e;
      this.r = r;
    }
    public void apply(Object rcvr)  {
      if (rcvr instanceof OpCRDT op) {
        op.removed(e, r);
      }
    }

    public String toString() {
      return "removed " + e + ((r == null)?"":(" " + String.join(" ", r))); 
    }
  }



}
