package com.alesgaroth.zuv;

public class ZVar<T> extends ZNode<T> {
  public ZVar(T t) {
      this.value.set(t, null);
  }

  public void set(T t, ZQueue q) {
      this.value.set(t, q);
  }

  public void invalidate(ZQueue q) {
      this.value.invalidate(q);
  }

}
