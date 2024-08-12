package com.alesgaroth.zuv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZGraphNode extends ZNode {
  Map<String, ZNode> children = new HashMap<>();
  ZValue[] outputs;
  ZValue childrenOutput = new ZValue(children, this);

  public ZGraphNode(int inputs, int outputs) {
    super(inputs, outputs);
    this.outputs = new ZValue[outputs];
  }

  void addAsChild(ZValue t, ZQueue q) {
    // find node this Value is the output of, and add it as a child, if it's not already the child of something else.
    ZNode zn = t.parent();
    addNode(zn, q);
  }

  private void addOutput(ZValue output, int i) {
    outputs[i] = output;
  }

  public void setReturnValue(ZValue input, int i, ZQueue q) {
    addAsChild(input, q);
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

  public void addNewNode(ZQueue q) {
    ZNode var1 = new ZGraphNode(0, 0);
    addNode(var1, q);
  }

  public void addNode(ZNode var1, ZQueue q) {
    if (var1.parent() == null) {
      var1.setParent(this);
    } else if (var1.parent() != this) {
      throw new GraphMismatchException();
    }
    if (children.put(var1.name(), var1) != var1) {
      // child added, notify listeners
      childrenOutput.set(children, q);
    }
  }

  public List<ZNode> children() {
    return new ArrayList<>(children.values());
  }

  public void addChildrenListener(ZListener listener) {
    childrenOutput.addListener(listener);
  }

  public ZNode getByName(String name) {
     ZNode child =  children.get(name);
     if (child == null) {
        throw new ZPathException("looking for " + name + " in " + getPath() + " from " +
           String.join(", ", children.keySet()));
     }
     return children.get(name);
  }

  @Override
  public String toString() {
    return getPath() + "#" + super.toString();
  }
  public String getPath() {
    return super.getPath() + "/";
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
