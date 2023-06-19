package com.alesgaroth.zuv;

public class ZConstant<T> {
  T value;

  ZConstant(T value) {
  	this.value = value;
  }

  T fetch() {
  	return value;
  }
}
