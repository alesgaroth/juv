package com.alesgaroth.zuv;

public class ZConstant<T> {
  ZValue<T> value;

  public ZConstant(T value) {
  	this.value = new ZValue(value);
  }

  public void addListener(ZListener<T> o) {
  	value.addListener(o);
  }

  public ZValue<T> output(int outputNum) {
  	return value;
  }
}
