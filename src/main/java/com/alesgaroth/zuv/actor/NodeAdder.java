package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZNode;
import com.alesgaroth.zuv.ZQueue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NodeAdder implements ZQueue.Executable {
  Set<NodeAdditionListener> listeners = new HashSet<>();
  List<AddNodeRequest> requests = new ArrayList<>();

  public ZNode addNode(ZGraphNode parent, ZQueue q) {
    ZNode n = new ZNode(0, 0);
    parent.addNode(n, q);
    return n;
  }
  public void add(AddNodeRequest req) {
    requests.add(req);
  }
  private void notifyListeners() {
    NodeAddedEvent nae = new NodeAddedEvent() {};
    for(var l: listeners) {
      l.nodeAdded(nae);
    }
  }

  public void addListener(NodeAdditionListener l) {
    listeners.add(l);
  }

  public void execute(ZQueue q) {
    while (!requests.isEmpty()) {
      AddNodeRequest req = requests.remove(0);
      ZGraphNode parent = q.getRoot();
      parent.addNode(new ZGraphNode(0, 0), q);
      notifyListeners();
    }
  }
}
