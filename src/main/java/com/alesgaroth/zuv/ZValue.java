package com.alesgaroth.zuv;

import java.util.HashSet;
import java.util.Set;

public class ZValue<T> {
  T value;
  final ZNode<T> parent;
  Set<ZListener<T>> listeners = new HashSet<>();

  public ZValue(T value, ZNode<T> parent) {
  	this.value = value;
    this.parent = parent;
  }

  public void invalidate(ZQueue q) {
    value = null;
    for (ZListener<T> listener: listeners)
      listener.valueInvalidated(q);
  }

  public void set(T value, ZQueue q) {
    this.value = value;
    for (ZListener<T> listener: listeners)
      listener.valueChanged(q);
  }

  public ZNode<T> parent() {
    return parent;
  }

  public void addListener(ZListener<T> o) {
    if (o == null)
      throw new NullPointerException("null listeners are forbidden");
    listeners.add(o);
  }

  public T fetch(ZQueue q) {
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
