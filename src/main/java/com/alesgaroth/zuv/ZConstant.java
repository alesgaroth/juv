package com.alesgaroth.zuv;

public class ZConstant extends ZNode {

  public ZConstant(Object value) {
        super(0, 1);
        this.value.set(value, null);
  }

}
