package com.alesgaroth.zuv;

public class ZVar<T> extends ZNode<T> {
  public ZVar(T t) {
  	this.value.set(t);
  }

  public void set(T t) {
  	this.value.set(t);
  }

  public void invalidate() {
  	this.value.invalidate();
  }
}
