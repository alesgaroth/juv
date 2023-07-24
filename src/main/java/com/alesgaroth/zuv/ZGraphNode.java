package com.alesgaroth.zuv;

import java.util.LinkedHashSet;
import java.util.Set;

public class ZGraphNode<T> extends ZNode<T> {
  Set<ZNode<T>> children = new LinkedHashSet<>();
  ZValue<T> inputToReturnValue;
  
  public ZGraphNode(int inputs, int outputs) {
    super(inputs, outputs);
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
    inputToReturnValue = input;
    //value.set(input.fetch());
    ZGraphNode<T> gn = this;
    input.addListener(new ZListener<T>() {

      @Override
      public void valueChanged(ZQueue q) {
        //value.set(q);
        // Enqueue self so we can pass the info on to others
        msg_changed = true;
        q.doLater(gn);
      }

      @Override
      public void valueInvalidated(ZQueue q) {
        // value.invalidate();
        // Enqueue self so we can pass the info on to others
        msg_invalid = true;
        q.doLater(gn);
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

  @Override
  public void execute(ZQueue q) {
    q.prepend(inputToReturnValue.parent);
    if (!msg_invalid && msg_changed && input != null) {
      value.set(input.fetch(q), q);
    }
    super.execute(q);
  }
}
