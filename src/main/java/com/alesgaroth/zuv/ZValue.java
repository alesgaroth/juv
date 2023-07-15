package com.alesgaroth.zuv;

import java.util.HashSet;
import java.util.Set;

public class ZValue<T> {
  T value;
  Set<ZListener<T>> listeners = new HashSet<>();

  public ZValue(T value) {
  	this.value = value;
  }

  public void invalidate() {
    value = null;
	for (ZListener<T> listener: listeners)
	  listener.valueInvalidated();
  }

  public void set(T value) {
  	this.value = value;
	  for (ZListener<T> listener: listeners)
	    listener.valueChanged(value);
  }

  public void addListener(ZListener<T> o) {
  	if (o == null)
      throw new NullPointerException("null listeners are forbidden");
	  listeners.add(o);
  }

  public T fetch() {
  	return value;
  }

  public boolean isInvalid() {
    return value == null;
  }
}
