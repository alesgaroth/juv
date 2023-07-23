package com.alesgaroth.zuv;

public interface ZListener<T> {
  public void valueChanged(ZQueue q);
  public void valueInvalidated(ZQueue q);
}
