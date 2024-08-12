package com.alesagorth.zuv.threelayer.operator.math;

import com.alesagorth.zuv.threelayer.ConnectionInstance;
import com.alesagorth.zuv.threelayer.Node;
import com.alesagorth.zuv.threelayer.NodeInstance;

public class AddNode extends Node {
  void doCalculations(NodeInstance ni) {
    int value = 0;
    for(ConnectionInstance upstr:  ni.getUpstreams()) {
      value += upstr.getValue();
    }
    ConnectionInstance out = ni.getDownstream(0);
    out.setValue(value);
  }
}
