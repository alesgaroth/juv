package com.alesgaroth.zuv;

public class ZNode<T> {
  protected ZValue<T> value;
  ZValue<T> input;
  ZGraphNode<T> parent;

  boolean msg_wanted = false, msg_invalid = false, msg_changed = false;

  protected ZNode() {
    value = new ZValue<T>(null, this);
  }

  public void addListener(ZListener<T> o) {
    value.addListener(o);
  }

  public ZValue<T> output(int outputNum) {
    return value;
  }

  void setInput(ZValue<T> v, int input) {
    this.input = v;
  }

  public ZGraphNode<T> parent() {
    return parent;
  }

  public void setParent(ZGraphNode<T> zGraphNode) {
    parent = zGraphNode;
  }

  public void execute(ZQueue q) {
      msg_wanted = false;
      msg_invalid = false;
      msg_changed = false;
  } // for const and var don't do anything
}
