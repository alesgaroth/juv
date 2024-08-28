package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmInstance {
  Map<Node, NodeInstance> nis = new HashMap<>();
  static Map<Class<? extends Node>, Class<? extends NodeInstance>> basemap = Map.of(Node.class, NodeInstance.class);
  InstanceFactory creator;

  static public interface InstanceFactory {
    NodeInstance createNode(Node n);
    ConnectionInstance createConnection(Connection output);
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
  public List<NodeInstance> instantiate(Iterable<Node> set) {
    List<NodeInstance> list = new ArrayList<>();
    for(Node n: set) 
      list.add(createNodeAndItsConnection(n));
    return list;
  }

  private NodeInstance createNodeAndItsConnection(Node n) {
    NodeInstance ni = createNodeIfAbsent(n);
    for(int i = 0; i < n.getNumOutputs(); i += 1) 
     ni.setOutput(createConnectionAndNodes(n.getOutput(i)), i);
    return ni;
  }

  private ConnectionInstance createConnectionAndNodes(Connection output) {
    ConnectionInstance conn = creator.createConnection(output);
    for(Connection.NodePort np: output.getListeners()) 
      conn.connectDownStreamNode(np, createNodeIfAbsent(np.node()));
    return conn;
  }

  NodeInstance createNodeIfAbsent(Node n) {
    return nis.computeIfAbsent(n, m -> creator.createNode(m));
  }

}
