package com.alesgaroth.zuv.instance;
import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;

import java.util.Map;


public class InstanceMapFactory implements AlgorithmInstance.InstanceFactory {
  Map<Class<? extends Node>, Class<? extends NodeInstance>> classMap;
  public InstanceMapFactory(Map<Class<? extends Node>, Class<? extends NodeInstance>> m) {
    this.classMap = m;
  }
  public NodeInstance createNode(Node n) {
    Class<?> clz = n.getClass();
    try {
      return classMap.get(clz).getDeclaredConstructor(clz).newInstance(n);
    } catch (Exception e) {
      throw new RuntimeException("can't get declared constructor for " + clz + " from " + classMap, e);
    }
  }

  public ConnectionInstance createConnection(Connection output) {
    return new ConnectionInstance(output);
  }
}
