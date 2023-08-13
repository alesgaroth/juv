package com.alesgaroth.zuv;

public interface ZListener {
  public void valueChanged(ZQueue q);
  public void valueInvalidated(ZQueue q);
}
