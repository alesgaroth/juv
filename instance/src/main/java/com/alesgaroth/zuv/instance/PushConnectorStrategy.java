package com.alesgaroth.zuv.instance;

import java.util.concurrent.Executor;

public class PushConnectorStrategy implements ConnectionInstance.ConnectorStrategy  {

  final Executor executor;
  public PushConnectorStrategy(Executor executor) {
    this.executor = executor;
  }

  public void update(ConnectionInstance ci) {
    for(NodeInstance listener: ci.getListeners()){
      executor.execute(listener);
    }
  }

  public void calcValue(ConnectionInstance ci, NodeInstance upstream) {
  }

  public void invalidate(ConnectionInstance ci, NodeInstance upstream) {
  }
}
