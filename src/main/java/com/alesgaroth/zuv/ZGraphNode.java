package com.alesgaroth.zuv;

public class ZGraphNode<T> extends ZNode<T> {
  public ZGraphNode(int inputs, int outputs) {
  }

  public ZValue<T> input(int i) {
    return input;
  }

  public void setOutput(ZValue<T> input, int i) {
    value.set(input.fetch());
    input.addListener(new ZListener<T>() {

      @Override
      public void valueChanged(T q) {
        value.set(q);
      }

      @Override
      public void valueInvalidated() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'valueInvalidated'");
      }

    });
  }
}
