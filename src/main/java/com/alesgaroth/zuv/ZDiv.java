package com.alesgaroth.zuv;

public class ZDiv extends ZNode {

  public ZDiv() {
    super(2, 1);
    output(0).set((Integer)8, null);
  }

  @Override
  public void execute(ZQueue q) {
    if (parameter(0) != null && parameter(1) != null) {
      output(0).set((Integer)(((int)parameter(0).fetch(q))/((int)parameter(1).fetch(q))), q);
    }
    super.execute(q);
  }

}
