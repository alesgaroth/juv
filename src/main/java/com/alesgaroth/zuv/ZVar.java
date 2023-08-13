package com.alesgaroth.zuv;

public class ZVar extends ZNode {
  public ZVar(Object t) {
      super(0, 1);
      output(0).set(t, null);
  }

  public void set(Object t, ZQueue q) {
      output(0).set(t, q);
  }

  public void invalidate(ZQueue q) {
      output(0).invalidate(q);
  }

}
