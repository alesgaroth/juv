package com.alesgaroth.zuv.design;

import com.alesgaroth.zuv.index.Index;


import java.util.ArrayList;
import java.util.List;

public class Func extends Extensible implements ZNode<Func> {

  List<Value> outboundValues;
  int numInputs;
  String name;

  static long funcCounter;

  public Func(int numInputs, int numOutputs, Index parent) {
    this(numInputs, numOutputs);
    String id =  parent.getReplicaName() + ":" +  (funcCounter ++);
    this.name = id;
    parent.addIndex(id);
  }
  private Func(int numInputs, int numOutputs) {
    this.numInputs = numInputs;
    String id =  "func:" +  (funcCounter ++);
    this.name = id;
    outboundValues = new ArrayList<Value>(numOutputs);
    for(int i = 0; i < numOutputs; i += 1) {
      outboundValues.add(new Value("output/"+ i));
    }
  }
  public Func(String name, int numInputs, int numOutputs) {
    this(numInputs, numOutputs);
    this.name = name;
  }
  public Func(String name, int numInputs, int numOutputs, Index parent) {
    this(numInputs, numOutputs, parent);
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public String toString() {
    return "Func: " + getName() + " " + numInputs + " -> " + getNumOutputs();
  }

  public void dependOn(int input, Func upstream, int output) {
    if (!validPut(input, numInputs)) 
      throw new BadValueException();

    upstream.getOutput(output).addListener(this, input);
  }

  public void addOutput() {
    outboundValues.add(new Value("output/" + outboundValues.size()));
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

  public void removeOutput(int output) {
    if (output > getNumOutputs()) return;
    outboundValues.get(output).removeAllListeners();
  }

  public void removeListenersTo(Func func) { 
    for(Value v: outboundValues) {
      v.removeListenersTo(func);
    }
    //if (func != this) {
      //throw new RuntimeException("removed " + func  + ": " + this  + ".listeners now: " + outboundValues);
    //}
  }

  public Iterable<Value.FuncPort> getOutputFuncs(int i) {
    Value val = getOutput(i);
    return val.getListeners();
  }

  protected Func withExtensions(Func f) {
    for (Extensible.Extension ex: f.getExtensions() ) {
      if (ex instanceof Extensible.CloneableExtension<?> cex) {
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
    static final long serialVersionUID = 4;
  }
}
