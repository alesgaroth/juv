package com.alesgaroth.zuv.instance;
public class PullConnectorStrategy implements ConnectionInstance.ConnectorStrategy  {
  public void update(ConnectionInstance ci) {
  }

  public void calcValue(ConnectionInstance ci, NodeInstance upstream) {
    upstream.run();
  }

  public void invalidate(ConnectionInstance ci, NodeInstance upstream) {
  }
}
