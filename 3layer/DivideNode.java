package com.alesagorth.zuv.threelayer.operator.math;

import com.alesagorth.zuv.threelayer.ConnectionInstance;
import com.alesagorth.zuv.threelayer.Node;
import com.alesagorth.zuv.threelayer.NodeInstance;

public class DivideNode extends Node {
  void doCalculations(NodeInstance ni) {

    ConnectionInstance numerator = ni.getUpstream(0);
    ConnectionInstance denominator = ni.getUpstream(1);
    int value = numerator.getValue()/denominator.getValue();
    int remainder = numerator.getValue()%denominator.getValue();

    ConnectionInstance valueCI = ni.getDownstream(0);
    ConnectionInstance remainderCI = ni.getDownstream(1);
    valueCI.setValue(value);
    remainderCI.setValue(remainder);
  }
}
