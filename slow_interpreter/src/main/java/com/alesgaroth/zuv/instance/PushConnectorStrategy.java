package com.alesgaroth.zuv.instance;

import java.util.concurrent.Executor;

public class PushConnectorStrategy implements ValueInstance.ConnectorStrategy  {

  final Executor executor;
  public PushConnectorStrategy(Executor executor) {
    this.executor = executor;
  }

  public void update(ValueInstance ci) {
    for(FuncInstance listener: ci.getListeners()){
      executor.execute(listener);
    }
  }

  public void calcValue(ValueInstance ci, FuncInstance upstream) {
  }

  public void invalidate(ValueInstance ci, FuncInstance upstream) {
  }
}
