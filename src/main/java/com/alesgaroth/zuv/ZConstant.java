package com.alesgaroth.zuv;

public class ZConstant<T> extends ZNode<T> {

  public ZConstant(T value) {
        super(0, 1);
        this.value.set(value, null);
  }

}
