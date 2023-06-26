package com.alesgaroth.zuv;

public class ZValue<T> {
  T value;
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
