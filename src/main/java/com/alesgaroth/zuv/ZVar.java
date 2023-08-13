package com.alesgaroth.zuv;

public class ZVar extends ZNode {
  public ZVar(Object t) {
      super(0, 1);
      this.value.set(t, null);
  }

  public void set(Object t, ZQueue q) {
      this.value.set(t, q);
  }

  public void invalidate(ZQueue q) {
      this.value.invalidate(q);
  }

}
