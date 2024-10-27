package com.alesgaroth.zuv.instance;
import java.util.concurrent.Executor;
import com.alesgaroth.zuv.design.Func;

public class CachedPullConnectorStrategy implements ValueInstance.ConnectorStrategy  {
  Executor ex;

  public CachedPullConnectorStrategy(Executor ex) {
    this.ex = ex;
  }

  public void update(ValueInstance ci) {
    for(FuncInstance<Func> listener: ci.getListeners()){
      ex.execute(() -> {
        for(ValueInstance op: listener.getOutputs()) {
              op.invalidate();
              update(op);
        }
      });
    }
  }

  public void calcValue(ValueInstance ci, FuncInstance upstream) {
    if (!ci.isReady()) {
      upstream.run();
    }
  }

  public void invalidate(ValueInstance ci, FuncInstance upstream) {
  }
}
