package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZNode;

import java.util.HashSet;
import java.util.Set;

public class NodeAdder {
   Set<NodeAdditionListener> listeners = new HashSet<>();

   public ZNode addNode(ZGraphNode parent) {
      ZNode n = new ZNode(0, 0);
      parent.addNode(n);
      return n;
   }
   public void add(AddNodeRequest req) {
      NodeAddedEvent nae = new NodeAddedEvent() {};
      for(var l: listeners) {
        l.nodeAdded(nae);
      }
   }

   public void addListener(NodeAdditionListener l) {
     listeners.add(l);
   }
}
