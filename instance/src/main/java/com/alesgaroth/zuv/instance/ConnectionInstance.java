package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConnectionInstance {

  static final private Object uninitialized = new Object();
  final NodeInstance upstream;
  List<NodeInstance> listeners = new ArrayList<>();
  private Object value = uninitialized;
  private ConnectorStrategy strat;

  final static private ConnectorStrategy noConnectorStrategy = new ConnectorStrategy() {
    public void update(ConnectionInstance ci) {
    }
    public void calcValue(ConnectionInstance ci, NodeInstance upstream) {
    }
  };

  public ConnectionInstance(NodeInstance upstream, ConnectorStrategy cs) {
    strat = cs;
    this.upstream = upstream;
  }

  public ConnectionInstance(NodeInstance upstream) {
    this(upstream, noConnectorStrategy);
  }

  public Iterable<NodeInstance> getListeners() {
    return Collections.unmodifiableList(listeners);
  }

  void connectDownStreamNode(Connection.NodePort np, NodeInstance ni2) {
    ni2.setInput(this, np.input());
    listeners.add(ni2);
  }

  public void update(Object value) {
    this.value = value;
    strat.update(this);
  }

  public void invalidate() {
    this.value = uninitialized;
  }

  public Object getValue() {
    strat.calcValue(this, upstream);
    if (!isReady()) 
      throw new IllegalStateException();
    return this.value;
  }

  public boolean isReady() {
    return this.value != uninitialized;
  }

  static public interface ConnectorStrategy {
    void update(ConnectionInstance ci);
    void calcValue(ConnectionInstance ci, NodeInstance upstream);
  }

}

