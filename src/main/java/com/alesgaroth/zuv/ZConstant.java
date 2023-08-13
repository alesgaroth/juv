package com.alesgaroth.zuv;

public class ZConstant extends ZNode {

  public ZConstant(Object value) {
        super(0, 1);
        output(0).set(value, null);
  }

}
