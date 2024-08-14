package com.alesgaroth.zuv.design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Connection {
  List<NodePort> listeners = new ArrayList<>();

  public Iterable<NodePort> getListeners(){
      return Collections.unmodifiableList(listeners);
  }

  void addListener(Node l, int input) {
    listeners.add(new NodePort(l, input));
  }

  public record NodePort(Node l, int input) {}
}
