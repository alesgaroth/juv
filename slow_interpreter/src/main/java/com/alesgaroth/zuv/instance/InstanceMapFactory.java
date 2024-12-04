package com.alesgaroth.zuv.instance;
import com.alesgaroth.zuv.design.Value;
import com.alesgaroth.zuv.design.Func;

import java.util.Map;


public class InstanceMapFactory implements AlgorithmInstance.InstanceFactory {
  Map<Class<? extends Func>, Class<? extends FuncInstance>> classMap;
  public InstanceMapFactory(Map<Class<? extends Func>, Class<? extends FuncInstance>> m) {
    this.classMap = m;
  }
  public FuncInstance createFunc(Func n) {
    Class<?> clz = n.getClass();
    try {
      FuncInstance fi =   classMap.get(clz).getDeclaredConstructor(clz).newInstance(n);
      for(int i = 0; i < n.getNumOutputs(); i += 1)  {
        ValueInstance value = createValue(fi, i);
        fi.setOutput(value, i);
      }
      return fi;
    } catch (Exception e) {
      throw new RuntimeException("can't get declared constructor for " + clz + " from " + classMap, e);
    }
  }

  public ValueInstance createValue(FuncInstance ni, int output) {
    return new ValueInstance(ni);
  }
}
