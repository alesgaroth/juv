package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Func;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmInstance {
  Map<Func, FuncInstance<? extends Func>> nis = new HashMap<>();
  static Map<Class<? extends Func>, Class<? extends FuncInstance>> basemap = Map.of(Func.class, FuncInstance.class);
  InstanceFactory creator;

  static public interface InstanceFactory {
    FuncInstance createFunc(Func n);
    ConnectionInstance createConnection(FuncInstance ni, int output);
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
  //public static List<FuncInstance> cloneOutputs(Iterable<Func> set) {
    //AlgorithmInstance instance = new AlgorithmInstance();
    //return instance.instantiate(set);
  //}

  /**
   * creates a FuncInstance for each Func and returns list with matching
   * FuncInstances in the same order as the given iterable returns them.
   */
  public <N extends Func> List<FuncInstance<N>> instantiate(Iterable<N> set) {
    List<FuncInstance<N>> list = new ArrayList<>();
    for(N n: set) 
      list.add(createFuncAndItsConnection(n));
    return list;
  }

  private <N extends Func> FuncInstance<N> createFuncAndItsConnection(N n) {
    FuncInstance<N> ni = createFuncIfAbsent(n);
    for(int i = 0; i < n.getNumOutputs(); i += 1)  {
     ConnectionInstance conn = creator.createConnection(ni, i);
     ni.setOutput(createFuncs(conn, n.getOutput(i)), i);
    }
    return ni;
  }

  private ConnectionInstance createFuncs(ConnectionInstance conn, Connection output) {
    for(Connection.FuncPort np: output.getListeners()) 
      conn.connectDownStreamFunc(np, createFuncIfAbsent(np.node()));
    return conn;
  }

  <N extends Func> FuncInstance<N> createFuncIfAbsent(N n) {
    return nis.computeIfAbsent(n, m -> creator.createFunc(m));
  }

}
