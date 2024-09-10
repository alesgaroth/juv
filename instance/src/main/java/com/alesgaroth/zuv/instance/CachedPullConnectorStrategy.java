package com.alesgaroth.zuv.instance;
import java.util.concurrent.Executor;

public class CachedPullConnectorStrategy implements ConnectionInstance.ConnectorStrategy  {
  Executor ex;

  public CachedPullConnectorStrategy(Executor ex) {
    this.ex = ex;
  }

  public void update(ConnectionInstance ci) {
    for(NodeInstance listener: ci.getListeners()){
      ex.execute(() -> {
        for(ConnectionInstance op: listener.getOutputs()) {
              op.invalidate();
              update(op);
        }
      });
    }
  }

  public void calcValue(ConnectionInstance ci, NodeInstance upstream) {
    if (!ci.isReady()) {
      upstream.run();
    }
  }
}
