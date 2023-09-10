package com.alesgaroth.zuv;

public class ZNode {
  private ZValue[] values;
  private ZValue[] inputs;
  ZGraphNode parent;

  boolean msg_wanted = false, msg_invalid = false, msg_changed = true;

  public ZNode(int inputs, int outputs) {
    values = new ZValue[outputs];
    for (int i = 0; i < outputs; i += 1) {
      values[i] = new ZValue(null, this);
    }
    this.inputs = new ZValue[inputs];
  }

  public void addListener(ZListener o) {
    for (ZValue value: values) {
      value.addListener(o);
    }
  }

  public ZValue output(int outputNum) {
    return values[outputNum];
  }

  public ZValue parameter(int i) {
    return inputs[i];
  }

  protected int numOutputs() {
    return values.length;
  }

  public void setInput(ZValue v, int input) {
    inputs[input] = v;
  }

  public ZGraphNode parent() {
    return parent;
  }

  public void setParent(ZGraphNode zGraphNode) {
    parent = zGraphNode;
  }

  public void execute(ZQueue q) {
      msg_wanted = false;
      msg_invalid = false;
      msg_changed = false;
  } // for const and var don't do anything
}
