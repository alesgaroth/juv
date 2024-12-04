package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.AlgorithmCopier;
import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.design.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmInstance {
  static Map<Class<? extends Func>, Class<? extends FuncInstance>> basemap = Map.of(Func.class, FuncInstance.class);
  InstanceFactory creator;

  static public interface InstanceFactory extends AlgorithmCopier.FunclikeFactory<FuncInstance>{
    FuncInstance createFunc(Func n);
  }

  public AlgorithmInstance(InstanceFactory factory) {
    creator = factory;
  }

  public AlgorithmInstance(Map<Class<? extends Func>, Class<? extends FuncInstance>> m) {
    this(new InstanceMapFactory(m));
  }

  public AlgorithmInstance() {
    this(basemap);
  }

  /**
   * creates a FuncInstance for each Func and returns list with matching
   * FuncInstances in the same order as the given iterable returns them.
   */
  public <N extends Func> List<FuncInstance<N>> instantiate(Iterable<N> set) {
    return new AlgorithmCopier(creator).instantiate(set);
  }
}
