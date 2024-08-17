package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;
import java.util.HashMap;
import java.util.Map;

public class AlgorithmInstance {
  Iterable<Node> set;
  Map<Node, NodeInstance> nis = new HashMap<>();
  public AlgorithmInstance (Iterable<Node> set) {
    this.set = set;
  }

  public static Iterable<NodeInstance> cloneOutputs(Iterable<Node> set) {
    AlgorithmInstance instance = new AlgorithmInstance(set);
    return instance.instantiate();
  }

  public Iterable<NodeInstance> instantiate() {
    for(Node n: set) 
      createNodeAndItsConnection(n);
    return nis.values();
  }

  private void createNodeAndItsConnection(Node n) {
    NodeInstance ni = createNodeIfNotAlreadCreated(n);
    for(int i = 0; i < ni.connections.length; i += 1) 
     ni.connections[i] = createConnection(n.getOutput(i));
  }

  private ConnectionInstance createConnection(Connection output) {
    ConnectionInstance conn = new ConnectionInstance(output);
    for(Connection.NodePort np: output.getListeners()) 
      conn.connectDownStreamNode(np, createNodeIfNotAlreadCreated(np.node()));
    return conn;
  }

  NodeInstance createNodeIfNotAlreadCreated(Node n) {
    return nis.computeIfAbsent(n, (k) -> new NodeInstance(k));
  }
  
}
