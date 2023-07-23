package com.alesgaroth.zuv;

public class ZDiv extends ZNode<Integer> {

  ZValue<Integer> [] inputs = new ZValue[2];

  public ZDiv() {
    this.value.set((Integer)8, null);
  }

  void setInput(ZValue<Integer> v, int input, ZQueue q) {
    inputs[input] = v;
  }

  @Override
  public void execute(ZQueue q) {
    if (inputs[0] != null && inputs[1] != null) {
      this.value.set((Integer)(((int)inputs[0].fetch(q))/(inputs[1].fetch(q))), q);
    }
    super.execute(q);
  }

}
