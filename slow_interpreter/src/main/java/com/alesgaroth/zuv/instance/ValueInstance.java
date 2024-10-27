package com.alesgaroth.zuv.instance;

import com.alesgaroth.zuv.design.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ValueInstance {

  static final private Object uninitialized = new Object();
  final FuncInstance upstream;
  List<FuncInstance> listeners = new ArrayList<>();
  private Object value = uninitialized;
  private ConnectorStrategy strat;

  final static private ConnectorStrategy noConnectorStrategy = new ConnectorStrategy() {
    public void update(ValueInstance ci) {
    }
    public void calcValue(ValueInstance ci, FuncInstance upstream) {
    }
    public void invalidate(ValueInstance ci, FuncInstance upstream) {
    }
  };

  public ValueInstance(FuncInstance upstream, ConnectorStrategy cs) {
    strat = cs;
    this.upstream = upstream;
  }

  public ValueInstance(FuncInstance upstream) {
    this(upstream, noConnectorStrategy);
  }

  public Iterable<FuncInstance> getListeners() {
    return Collections.unmodifiableList(listeners);
  }

  void connectDownStreamFunc(Value.FuncPort fp, FuncInstance ni2) {
    ni2.setInput(this, fp.input());
    listeners.add(ni2);
  }

  public void update(Object value) {
    if (this.value != value) {
      this.value = value;
      strat.update(this);
    }
  }

  public void invalidate() {
    this.value = uninitialized;
    strat.invalidate(this, upstream);
  }

  public Object getValue() {
    strat.calcValue(this, upstream);
    if (!isReady()) 
      throw new IllegalStateException();
    return this.value;
  }

  public boolean isReady() {
    return this.value != uninitialized;
  }

  static public interface ConnectorStrategy {
    void update(ValueInstance ci);
    void calcValue(ValueInstance ci, FuncInstance upstream);
    void invalidate(ValueInstance ci, FuncInstance upstream);
  }

}

