package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.design.Funclike;
import com.alesgaroth.zuv.design.Value;

import java.util.Arrays;
import java.util.Collections;

// A FuncInstance is analogous to a stack frame in a normal running system
public class FuncInstance<N extends Func> implements Runnable, Funclike<FuncInstance> {
  N design;
  ValueInstance [] values;
  ValueInstance [] upstreams;

  public FuncInstance(N design) {
    this.design = design;
    values = new ValueInstance[design.getNumOutputs()];
    upstreams = new ValueInstance[design.getNumInputs()];
  }

  public N getFunc() {
    return design;
  }

  public ValueInstance getOutput(int output) {
    if (!Func.validPut(output, values.length)) {
      throw new Func.BadValueException();
    }
    return values[output];
  }

  public Iterable<ValueInstance> getOutputs() {
    return Collections.unmodifiableList(Arrays.asList(values));
  }

  public Iterable<ValueInstance> getInputs() {
    return Collections.unmodifiableList(Arrays.asList(upstreams));
  }

  public void setOutput(ValueInstance ci, int output) {
    if (!Func.validPut(output, values.length)) {
      throw new Func.BadValueException();
    }
    values[output] = ci;
  }

  public ValueInstance getInput(int input) {
    if (!Func.validPut(input, upstreams.length)) {
      throw new Func.BadValueException();
    }
    return upstreams[input];
  }

  public boolean inputsReady() {
    for(ValueInstance ci: getInputs()) {
      if (!ci.isReady()){
        return false;
      }
    }
    return true;
  }

  public void setInput(ValueInstance ci, int input) {
    if (!Func.validPut(input, upstreams.length)) {
      throw new Func.BadValueException();
    }
    upstreams[input] = ci;
  }

  public void dependOn(int input, FuncInstance source, int output) {
    source.getOutput(output).connectDownStreamFunc(input, this);
  }

  public void run() {
  }
}
