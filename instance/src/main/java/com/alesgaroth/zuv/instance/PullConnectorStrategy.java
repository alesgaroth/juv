package com.alesgaroth.zuv.instance;
public class PullConnectorStrategy implements ConnectionInstance.ConnectorStrategy  {
    public void update(ConnectionInstance ci) {
    }
    public void calcValue(NodeInstance upstream) {
      upstream.run();
    }
}
