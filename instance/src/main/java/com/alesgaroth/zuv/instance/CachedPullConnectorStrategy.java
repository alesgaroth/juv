package com.alesgaroth.zuv.instance;
public class CachedPullConnectorStrategy implements ConnectionInstance.ConnectorStrategy  {
    public void update(ConnectionInstance ci) {
      for(NodeInstance listener: ci.getListeners()){
        for(ConnectionInstance op: listener.getOutputs()) {
          op.invalidate();
          update(op);
        }
      }
    }
    public void calcValue(ConnectionInstance ci, NodeInstance upstream) {
      if (!ci.isReady()) {
        upstream.run();
      }
    }
}
