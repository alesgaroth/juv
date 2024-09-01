package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConnectionInstance {

  List<NodeInstance> listeners = new ArrayList<>();
  private Object value;

  public Iterable<NodeInstance> getListeners() {
    return Collections.unmodifiableList(listeners);
  }

  void connectDownStreamNode(Connection.NodePort np, NodeInstance ni2) {
    ni2.setInput(this, np.input());
    listeners.add(ni2);
  }

  public void update(Object value) {
    this.value = value;
  }

  public Object getValue() {
    return this.value;
  }

  //public interface ConnectorStrategy {
    //void update(Object value);
    //Object getValue();
  //}

}

