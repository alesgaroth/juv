package com.alesgaroth.zuv;

import java.util.LinkedHashSet;
import java.util.Set;

public class ZGraphNode extends ZNode {
  Set<ZNode> children = new LinkedHashSet<>();
  ZValue[] outputs;

  public ZGraphNode(int inputs, int outputs) {
    super(inputs, outputs);
    this.outputs = new ZValue[outputs];
  }

  void addAsChild(ZValue t) {
    // find node this Value is the output of, and add it as a child, if it's not already the child of something else.
    ZNode zn = t.parent();
    addNode(zn);
  }

  private void addOutput(ZValue output, int i) {
    outputs[i] = output;
  }

  public void setReturnValue(ZValue input, int i) {
    addAsChild(input);
    addOutput(input, i);
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
    if (!msg_invalid && msg_changed ) {
      for (int i = 0; i < numOutputs(); i += 1) {
        output(i).set(outputs[i].fetch(q), q);
      }
    }
    super.execute(q);
  }
}
