package com.alesgaroth.zuv;

import java.util.HashSet;
import java.util.Set;

public class ZValue {
  Object value;
  final ZNode parent;
  Set<ZListener> listeners = new HashSet<>();

  public ZValue(Object value, ZNode parent) {
    this.value = value;
    this.parent = parent;
  }

  public void invalidate(ZQueue q) {
    value = null;
    for (ZListener listener: listeners)
      listener.valueInvalidated(q);
  }

  public void set(Object value, ZQueue q) {
    this.value = value;
    for (ZListener listener: listeners)
      listener.valueChanged(q);
  }

  public ZNode parent() {
    return parent;
  }

  public void addListener(ZListener o) {
    if (o == null)
      throw new NullPointerException("null listeners are forbidden");
    listeners.add(o);
  }

  public Object fetch(ZQueue q) {
    if (!isInvalid()) {
      parent.msg_wanted = true;
      q.prepend(parent);
    }
    return value;
  }

  public boolean isInvalid() {
    return value == null;
  }
}
