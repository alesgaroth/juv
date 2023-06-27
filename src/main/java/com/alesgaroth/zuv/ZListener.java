package com.alesgaroth.zuv;

public interface ZListener<T> {
	public void valueChanged(T q);
	public void valueInvalidated();
}
