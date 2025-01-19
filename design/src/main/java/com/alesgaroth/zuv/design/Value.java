package com.alesgaroth.zuv.design;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Value {
  List<FuncPort> listeners = new ArrayList<>();

  public Collection<FuncPort> getListeners(){
      return Collections.unmodifiableList(listeners);
  }

  void addListener(Func l, int input) {
    listeners.add(new FuncPort(l, input));
  }

  void removeAllListeners() {
    listeners.clear();
  }

  void removeListenersTo(Func func) {
    List<FuncPort> toRemove = new ArrayList<>();
    for(FuncPort fp: listeners) {
      if (fp.func == func) {
        toRemove.add(fp);
      }
    }
    listeners.removeAll(toRemove);
  }

  public record FuncPort(Func func, int input) {}
}
