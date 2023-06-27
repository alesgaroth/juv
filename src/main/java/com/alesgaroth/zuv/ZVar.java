package com.alesgaroth.zuv;

public class ZVar<T> extends ZNode<T> {
  public ZVar(T t) {
    super();
  	this.value = new ZValue<T>(t);
  }

}
