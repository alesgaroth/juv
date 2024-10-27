package com.alesgaroth.zuv.instance;
import java.util.concurrent.Executor;
import com.alesgaroth.zuv.design.Func;

public class CachedPullConnectorStrategy implements ConnectionInstance.ConnectorStrategy  {
  Executor ex;

  public CachedPullConnectorStrategy(Executor ex) {
    this.ex = ex;
  }

  public void update(ConnectionInstance ci) {
    for(FuncInstance<Func> listener: ci.getListeners()){
      ex.execute(() -> {
        for(ConnectionInstance op: listener.getOutputs()) {
              op.invalidate();
              update(op);
        }
      });
    }
  }

  public void calcValue(ConnectionInstance ci, FuncInstance upstream) {
    if (!ci.isReady()) {
      upstream.run();
    }
  }

  public void invalidate(ConnectionInstance ci, FuncInstance upstream) {
  }
}
