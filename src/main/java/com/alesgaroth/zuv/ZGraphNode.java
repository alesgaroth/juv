package com.alesgaroth.zuv;

import java.util.LinkedHashSet;
import java.util.Set;

public class ZGraphNode extends ZNode {
  Set<ZNode> children = new LinkedHashSet<>();
  ZValue inputToReturnValue;
  
  public ZGraphNode(int inputs, int outputs) {
    super(inputs, outputs);
  }

  void addAsChild(ZValue t) {
    // find node this Value is the output of, and add it as a child, if it's not already the child of something else.
    ZNode zn = t.parent();
    addNode(zn);
  }

  public void setReturnValue(ZValue input, int i) {
    addAsChild(input);
    inputToReturnValue = input;
    //value.set(input.fetch());
    ZGraphNode gn = this;
    input.addListener(new ZListener() {

      @Override
      public void valueChanged(ZQueue q) {
        //value.set(q);
        // Enqueue self so we can pass the info on to others
        msg_changed = true;
        q.enqueue(gn);
      }

      @Override
      public void valueInvalidated(ZQueue q) {
        // value.invalidate();
        // Enqueue self so we can pass the info on to others
        msg_invalid = true;
        q.enqueue(gn);
      }
    });
  }

  public void addNode(ZNode var1) {
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
    if (!msg_invalid && msg_changed && parameter(0) != null) {
      output(0).set(parameter(0).fetch(q), q);
    }
    super.execute(q);
  }
}
