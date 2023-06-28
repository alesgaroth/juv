package com.alesgaroth.zuv;

public class ZNode<T> {
  protected ZValue<T> value;
  ZValue<T> input;

  protected ZNode() {
  	value = new ZValue<T>(null);
  }

  public void addListener(ZListener<T> o) {
  	value.addListener(o);
  }

  public ZValue<T> output(int outputNum) {
  	return value;
  }

  void setInput(ZValue<T> v, int input) {
	this.input = v;
  }
}
