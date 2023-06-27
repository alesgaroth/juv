package com.alesgaroth.zuv;

public class ZValue<T> {
  T value;
  ZListener<T> listener;

  public ZValue(T value) {
  	this.value = value;
  }

  public void set(T value) {
  	this.value = value;
	if (listener != null)
	  listener.valueChanged();
  }

  public void addListener(ZListener<T> o) {
  	if (o == null) throw new NullPointerException("null listeners are forbidden");
	listener = o;
  }

  public T fetch() {
  	return value;
  }
}
