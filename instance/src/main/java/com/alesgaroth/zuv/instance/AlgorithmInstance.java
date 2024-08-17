package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;
import java.util.HashMap;
import java.util.Map;

public class AlgorithmInstance {
  public static Iterable<NodeInstance> cloneOutputs(Iterable<Node> set) {
    Map<Node, NodeInstance> nis = new HashMap<>();
    for(Node n: set) {
      NodeInstance ni = nis.computeIfAbsent(n, (k) -> new NodeInstance(k));
      for(int i = 0; i < ni.connections.length; i += 1) {
        ni.connections[i] = new ConnectionInstance(n.getOutput(i), nis);
        for(Connection.NodePort np: n.getOutput(i).getListeners()){
          NodeInstance ni2 = nis.computeIfAbsent(np.node(), (key) -> new NodeInstance(key));
          ni2.setInput(ni.connections[i], np.input());
          ni.connections[i].listeners.add(ni2);
        }
      }
    }
    return nis.values();
  }
}
