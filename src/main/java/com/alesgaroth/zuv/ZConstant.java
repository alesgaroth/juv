package com.alesgaroth.zuv;

public class ZConstant<T> {
  T value;

  public ZConstant(T value) {
  	this.value = value;
  }

  public T fetch() {
  	return value;
  }

  public void refresh() {
  }

  public void addListener(Object o) {
  }
}
