package com.alesgaroth.zuv;

public class ZDiv extends ZNode {

  ZValue [] inputs = new ZValue[2];

  public ZDiv() {
    super(2, 1);
    this.value.set((Integer)8, null);
  }

  void setInput(ZValue v, int input, ZQueue q) {
    inputs[input] = v;
  }

  @Override
  public void execute(ZQueue q) {
    if (inputs[0] != null && inputs[1] != null) {
      this.value.set((Integer)(((int)inputs[0].fetch(q))/((int)inputs[1].fetch(q))), q);
    }
    super.execute(q);
  }

}
