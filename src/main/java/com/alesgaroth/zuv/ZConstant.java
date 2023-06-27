package com.alesgaroth.zuv;

public class ZConstant<T> extends ZNode<T> {

  public ZConstant(T value) {
  	this.value = new ZValue<T>(value);
  }
}
