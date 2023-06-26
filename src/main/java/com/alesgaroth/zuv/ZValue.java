package com.alesgaroth.zuv;

import java.util.HashSet;
import java.util.Set;

public class ZValue<T> {
  T value;
  //Set<ZListener> listeners = new HashSet<>();
  public ZValue(T value) {
  	this.value = value;
  }

  public void addListener(ZListener<T> o) {
  	if (o == null) throw new NullPointerException("null listeners are forbidden");
  }

  public T fetch() {
  	return value;
  }
}
