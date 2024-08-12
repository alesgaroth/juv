package com.alesagorth.zuv.threelayer.operator;

//
// A ConnectionInstance is somewhat analagous to 
// an argument to a function or a return value

public class ConnectionInstance {
  Map<NodeInstance, Listener> listeners = new ConcurrentHashMap<>(); // layer 3
  Object value;
  boolean valueIsCurrent = false;

  public void setValue(Object value){
    this.value;
    valueIsCurrent = true;
    for(NodeInstance listener: listeners.getKeySet()) {
      executor.submit(listener);
    }
  }

  public Object getValue() {
    return value;
  }

  public Cancelable register(NodeInstance ni) {
    synchronized(listeners) {
      if (listeners.hasKey(ni)) {
        return listeners.get(ni);
      } else {
        Listener l = new Listener(ni);
        listeners.put(ni, l);
        return l;
      }
    }
  }

  public boolean anyListeners() {
    return !listeners.isEmpty();
  }


  class Listener implements Cancelable {
    NodeInstance ni;

    Listener(NodeInstance ni) {
      this.ni = ni;
    }

    @Override
    public boolean cancel() {
      listeners.remove(ni);
      if (!anyListeners())
        // layer 4
        valueIsCurrent = false; // if no one's listening, we're not keeping out value up to date
      return true;
    }

    @Override
    public boolean valueIsCurrent() {
      return valueIsCurrent; // layer 4
    }
  }
}
