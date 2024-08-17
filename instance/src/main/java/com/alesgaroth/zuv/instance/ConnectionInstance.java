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
  }

  public Iterable<NodeInstance> getListeners() {
    return Collections.unmodifiableList(listeners);
  }
}

