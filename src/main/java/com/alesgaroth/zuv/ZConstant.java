package com.alesgaroth.zuv;

public class ZConstant<T> {
  T value;

  public ZConstant(T value) {
  	this.value = value;
  }

  public T fetch() {
  	return value;
  }

  public void addListener(Object o) {
  	if (o == null) throw new NullPointerException("null listeners are forbidden");

  }
}
