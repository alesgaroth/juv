package com.alesgaroth.zuv;

public class ZVar<T> extends ZNode<T> {
  T var;
  public ZVar(T t) {
  	this.var = t;
  }

}
