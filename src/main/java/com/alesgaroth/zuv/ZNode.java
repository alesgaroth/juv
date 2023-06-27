package com.alesgaroth.zuv;

public class ZNode<T> {
  protected ZValue<T> value;

  protected ZNode() {
  	value = new ZValue<T>(null);
  }

  public void addListener(ZListener<T> o) {
  	value.addListener(o);
  }

  public ZValue<T> output(int outputNum) {
  	return value;
  }
}
