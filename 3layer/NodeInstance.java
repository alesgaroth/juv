package com.alesagorth.zuv.threelayer;

// A NodeInstance is analogous to a stack frame in a normal running system

// layer 2
public class NodeInstance implements Runnable {
  Set<Cancelable> upstream = new HashSet<>(); // layer 3
  List<ConnectionInstance> upstreamInstances = new ArrayList<>(); // layer 2
  List<ConnectionInstance> downstreamInstances = new ArrayList<>(); //layer 2
  Node design;

  public void run() {
    if (!anyListeners()) { // layer 3
      cancelUpstream();
      return;
    }
    if (upstream.size() < numUpstream()) { // layer 3
      registerUpstreams();
      return;
    }
    if (!allReady()) { // layer 2
      return;
    }
    doCalculations(); // layer 2
  }

  public boolean anyListeners() {
    for(ConnectionInstance out: getDownstreams()) {
      if (out.anyListeners()) { // layer 3
        return true;
      }
    }
    return false;
  }

  public int numUpstream(){
    return upstreamInstances.size();
  }
  public int numDownstream(){
    return downstreamInstances.size();
  }

  public ConnectionInstance getUpstream(int k) {
    return upstreamInstances.get(k);
  }
  public ConnectionInstance getDownstream(int k) {
    return downstreamInstances.get(k);
  }
  public Iterable<ConnectionInstance> getUpstreams() {
    return Collections.unmodifiableList(upstreamInstances);
  }
  public Iterable<ConnectionInstance> getDownstreams() {
    return Collections.unmodifiableList(downstreamInstances);
  }

  void cancelUpstream() { // layer 3
    for(Cancelable c: upstream) {
      c.cancel();
    }
    upstream.clear();
  }

  void doCalculations() { //layer 2
    design.doCalculations(this);
  }

  void registerUpstreams() { // layer 3
    for(ConnectionInstance ci: upstreamInstances) {
      upstream.add(ci.register(this)); // we can do this any number of times since each nodeinstance is only stored once.
    }
  }

  boolean allReady() { // layer 2
    if (upstream.size() < numUpstream()) { // layer 3
      return false;
    for(Cancelable c: upstream)
      if (!c.valueIsCurrent())
        return false;
    return true;
  }
}

