package com.alesgaroth.zuv.design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Value {
  List<FuncPort> listeners = new ArrayList<>();

  public Iterable<FuncPort> getListeners(){
      return Collections.unmodifiableList(listeners);
  }

  void addListener(Func l, int input) {
    listeners.add(new FuncPort(l, input));
  }

  public record FuncPort(Func func, int input) {}
}
