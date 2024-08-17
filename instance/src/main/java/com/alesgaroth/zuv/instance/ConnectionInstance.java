package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;
import com.alesgaroth.zuv.design.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConnectionInstance {

  Connection conn;
  List<NodeInstance> listeners = new ArrayList<>();

  ConnectionInstance(Connection conn, Map<Node, NodeInstance> algoInst) {
    this.conn = conn;
    for(Connection.NodePort np: conn.getListeners()){
      NodeInstance ni = algoInst.computeIfAbsent(np.node(), (key) -> new NodeInstance(key));
      ni.setInput(this, np.input());
      listeners.add(ni);
    }
  }

  public Iterable<NodeInstance> getListeners() {
    return Collections.unmodifiableList(listeners);
  }
}

