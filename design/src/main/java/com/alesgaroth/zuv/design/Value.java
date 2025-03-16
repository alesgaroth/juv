package com.alesgaroth.zuv.design;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Value {
  Set<FuncPort> listeners = new HashSet<>();
  Value(String path) {
  }

  public Collection<FuncPort> getListeners(){
      return Collections.unmodifiableSet(listeners);
  }

  public String toString() {
    return "" + listeners;
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
    //if (!toRemove.isEmpty()) {
      //throw new RuntimeException("Removed " + toRemove + " so now " + listeners);
    //}
  }

  public record FuncPort(Func func, int input) {}
}
