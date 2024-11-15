package com.alesgaroth.zuv.design;

public class Func extends Extensible {
  Value[] outboundValues;
  final int numInputs;

  public Func(int numInputs, int numOutputs) {
    this.numInputs = numInputs;
    outboundValues = new Value[numOutputs];
    for(int i = 0; i < numOutputs; i += 1) {
      outboundValues[i] = new Value();
    }
  }

  public void dependOn(int input, Func upstream, int output) {
    if (!validPut(input, numInputs)) 
      throw new BadValueException();

    upstream.getOutput(output).addListener(this, input);
  }

  public int getNumOutputs() {
    return outboundValues.length;
  }

  public int getNumInputs() {
    return numInputs;
  }

  public Value getOutput(int output) {
    if (!validPut(output, outboundValues.length)) 
      throw new BadValueException();

    return outboundValues[output];
  }

  static public boolean validPut(int num, int max) {
    return num >= 0 && max > num;
  }

  static public class BadValueException extends RuntimeException {
  }
}
