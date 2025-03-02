package com.alesgaroth.zuv.design;
public interface ZNode<T extends ZNode<T>> {
  void dependOn(int input, T source, int output);
}
