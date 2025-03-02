package com.alesgaroth.zuv.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmCopier<T extends ZNode<T>> {
  Map<Func, T> nis = new HashMap<>();
  ZNodeFactory<T> creator;

  static public interface ZNodeFactory<U> {
    U createFunc(Func n);
  }

  public AlgorithmCopier(ZNodeFactory<T> factory) {
    creator = factory;
  }


  /**
   * creates a FuncInstance for each Func and returns list with matching
   * FuncInstances in the same order as the given iterable returns them.
   */
  public <N extends Func> List<T> instantiate(Iterable<N> set) {
    List<T> list = new ArrayList<>();
    for(N n: set) 
      list.add(createFuncAndItsValue(n));
    return list;
  }

  private <N extends Func> T createFuncAndItsValue(N n) {
    T ni = createFuncIfAbsent(n);
    for(int i = 0; i < n.getNumOutputs(); i += 1)  {
     createFuncs(i, n.getOutputFuncs(i), ni);
    }
    return ni;
  }

  private void createFuncs(int output, Iterable<Value.FuncPort> listeners, T ni) {
    for(Value.FuncPort fp: listeners) 
      createFuncIfAbsent(fp.func()).dependOn(fp.input(), ni, output);
  }

  private <N extends Func> T createFuncIfAbsent(N n) {
    return nis.computeIfAbsent(n, m -> creator.createFunc(m));
  }
}
