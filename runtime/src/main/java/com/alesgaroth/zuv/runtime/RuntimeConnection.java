
package com.alesgaroth.zuv.runtime;

import com.alesgaroth.zuv.instance.ConnectionInstance;
import com.alesgaroth.zuv.instance.NodeInstance;

import java.util.concurrent.Executor;

public class RuntimeConnection extends ConnectionInstance {

  final NodeInstance upstream;
  final Executor executor;
  private Object value;

  public RuntimeConnection(Executor ex, NodeInstance upstream) {
    this.executor = ex;
    this.upstream = upstream;
  }

  public void update(Object value) {
    this.value = value;
    for(NodeInstance ni: getListeners()){
      executor.execute(ni);
    }
  }

  public Object getValue() {
    return this.value;
  }

}

