package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmInstance {
  Map<Node, NodeInstance<? extends Node>> nis = new HashMap<>();
  static Map<Class<? extends Node>, Class<? extends NodeInstance>> basemap = Map.of(Node.class, NodeInstance.class);
  InstanceFactory creator;

  static public interface InstanceFactory {
    NodeInstance createNode(Node n);
    ConnectionInstance createConnection(NodeInstance ni, int output);
  }

  public AlgorithmInstance(InstanceFactory factory) {
    creator = factory;
  }

  public AlgorithmInstance(Map<Class<? extends Node>, Class<? extends NodeInstance>> m) {
    this(new InstanceMapFactory(m));
  }

  public AlgorithmInstance() {
    this(basemap);
  }


  /**
   * creates a NodeInstance for each Node and returns list with matching
   * NodeInstances in the same order as the given iterable returns them.
   */
  //public static List<NodeInstance> cloneOutputs(Iterable<Node> set) {
    //AlgorithmInstance instance = new AlgorithmInstance();
    //return instance.instantiate(set);
  //}

  /**
   * creates a NodeInstance for each Node and returns list with matching
   * NodeInstances in the same order as the given iterable returns them.
   */
  public <N extends Node> List<NodeInstance<N>> instantiate(Iterable<N> set) {
    List<NodeInstance<N>> list = new ArrayList<>();
    for(N n: set) 
      list.add(createNodeAndItsConnection(n));
    return list;
  }

  private <N extends Node> NodeInstance<N> createNodeAndItsConnection(N n) {
    NodeInstance<N> ni = createNodeIfAbsent(n);
    for(int i = 0; i < n.getNumOutputs(); i += 1)  {
     ConnectionInstance conn = creator.createConnection(ni, i);
     ni.setOutput(createNodes(conn, n.getOutput(i)), i);
    }
    return ni;
  }

  private ConnectionInstance createNodes(ConnectionInstance conn, Connection output) {
    for(Connection.NodePort np: output.getListeners()) 
      conn.connectDownStreamNode(np, createNodeIfAbsent(np.node()));
    return conn;
  }

  <N extends Node> NodeInstance<N> createNodeIfAbsent(N n) {
    return nis.computeIfAbsent(n, m -> creator.createNode(m));
  }

}
