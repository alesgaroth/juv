package com.alesgaroth.zuv.design;

import java.util.ArrayList;
import java.util.List;

public class Func extends Extensible implements ZNode<Func> {
  List<Value> outboundValues;
  int numInputs;

  public Func(int numInputs, int numOutputs) {
    this.numInputs = numInputs;
    outboundValues = new ArrayList<Value>(numOutputs);
    for(int i = 0; i < numOutputs; i += 1) {
      addOutput();
    }
  }

  public void dependOn(int input, Func upstream, int output) {
    if (!validPut(input, numInputs)) 
      throw new BadValueException();

    upstream.getOutput(output).addListener(this, input);
  }

  public void addOutput() {
    outboundValues.add(new Value());
  }

  public int getNumOutputs() {
    return outboundValues.size();
  }

  public void addInput() {
    numInputs += 1;
  }

  public int getNumInputs() {
    return numInputs;
  }

  public Value getOutput(int output) {
    if (!validPut(output, outboundValues.size())) 
      throw new BadValueException();

    return outboundValues.get(output);
  }

  public Iterable<Value.FuncPort> getOutputFuncs(int i) {
    Value val = getOutput(i);
    return val.getListeners();
  }

  protected Func withExtensions(Func f) {
    for (Extensible.Extension ex: f.getExtensions() ) {
      if (ex instanceof Extensible.CloneableExtension cex) {
        extendWith(cex.shallowCopy());
      }
    }
    return this;
  }

  public Func shallowClone() {
    return new Func(this.numInputs, this.getNumOutputs()).withExtensions(this);
  }

  static public boolean validPut(int num, int max) {
    return num >= 0 && max > num;
  }

  static public class BadValueException extends RuntimeException {
  }
}
