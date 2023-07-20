package com.alesgaroth.zuv;

import java.util.LinkedHashSet;
import java.util.Set;

public class ZGraphNode<T> extends ZNode<T> {
  Set<ZNode<T>> children = new LinkedHashSet<>();
  
  public ZGraphNode(int inputs, int outputs) {
  }

  public ZValue<T> parameter(int i) {
    return input;
  }

  void addAsChild(ZValue<T> t) {
    // find node this Value is the output of, and add it as a child, if it's not already the child of something else.
    ZNode<T> zn = t.parent();
    addNode(zn);
  }

  public void setReturnValue(ZValue<T> input, int i) {
    addAsChild(input);
    value.set(input.fetch());
    input.addListener(new ZListener<T>() {

      @Override
      public void valueChanged(T q) {
        value.set(q);
      }

      @Override
      public void valueInvalidated() {
        value.invalidate();
      }

    });
  }

  public void addNode(ZNode<T> var1) {
    if (var1.parent() == this) {
      children.add(this);
    } else if (var1.parent() == null) {
      var1.setParent(this);
    } else {
      throw new GraphMismatchException();
    }

  }
}
