package com.alesgaroth.zuv.instance;
public class PullConnectorStrategy implements ValueInstance.ConnectorStrategy  {
  public void update(ValueInstance ci) {
  }

  public void calcValue(ValueInstance ci, FuncInstance upstream) {
    upstream.run();
  }

  public void invalidate(ValueInstance ci, FuncInstance upstream) {
  }
}
