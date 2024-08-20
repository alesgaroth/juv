package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmInstance {
  Map<Node, NodeInstance> nis = new HashMap<>();
  Map<Class<? extends Node>, Class<? extends NodeInstance>> classMap;
  static Map<Class<? extends Node>, Class<? extends NodeInstance>> basemap = Map.of(Node.class, NodeInstance.class);


  AlgorithmInstance(Map<Class<? extends Node>, Class<? extends NodeInstance>> m) {
    this.classMap = m;
  }

  AlgorithmInstance() {
    this(basemap);
  }


  /**
   * creates a NodeInstance for each Node and returns list with matching
   * NodeInstances in the same order as the given iterable returns them.
   */
  public static List<NodeInstance> cloneOutputs(Iterable<Node> set) {
    AlgorithmInstance instance = new AlgorithmInstance();
    return instance.instantiate(set);
  }

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
    NodeInstance ni = createNodeIfNotAlreadCreated(n);
    for(int i = 0; i < ni.connections.length; i += 1) 
     ni.connections[i] = createConnection(n.getOutput(i));
    return ni;
  }

  private ConnectionInstance createConnection(Connection output) {
    ConnectionInstance conn = new ConnectionInstance(output);
    for(Connection.NodePort np: output.getListeners()) 
      conn.connectDownStreamNode(np, createNodeIfNotAlreadCreated(np.node()));
    return conn;
  }

  NodeInstance createNodeIfNotAlreadCreated(Node n) {
    return nis.computeIfAbsent(n, this::createNode);
  }

  NodeInstance createNode(Node n) {
      return new NodeInstance(n);
  }
  
}
