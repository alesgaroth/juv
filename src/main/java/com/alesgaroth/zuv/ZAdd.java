package com.alesgaroth.zuv;

public class ZAdd extends ZNode {

    public ZAdd() {
        super(2, 1);
    }

    @Override
    public void execute(ZQueue q) {
        if (parameter(0) != null && parameter(1) != null) {
            output(0).set((Integer) (((int) parameter(0).fetch(q)) + ((int) parameter(1).fetch(q))), q);
        }
        super.execute(q);
    }

    @Override
    public String toString() {
      return "ZAdd<" + parameter(0).parent() + " + " + parameter(1).parent() + ">";
    }

}
