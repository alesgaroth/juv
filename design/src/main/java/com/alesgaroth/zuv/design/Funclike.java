package com.alesgaroth.zuv.design;
public interface Funclike<T extends Funclike> {
  void dependOn(int input, T source, int output);
}
