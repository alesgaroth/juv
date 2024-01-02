package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZNode;
import com.alesgaroth.zuv.ZQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NodeAdderTest implements NodeAdditionListener {

  boolean receivedNewNodeEvent = false;

  @Test
  void canAddANode() {
     NodeAdder adder = new NodeAdder();
     ZGraphNode g = new ZGraphNode(0, 0);
     ZNode node = adder.addNode(g);
     Assertions.assertNotNull(node);
     Assertions.assertEquals(g, node.parent());
  }

  @Test
  void canCreateNodeViaAddNodeRequest() {
    AddNodeRequest anr = new AddNodeRequest();
    NodeAdder adder = new NodeAdder();
    adder.addListener(this);
    anr.parent = "/";
    adder.add(anr);
    ZQueue q = new ZQueue();
    q.enqueue(adder);
    q.runTillEmpty();
     // TODO: verify that node was created 
     // TODO: Verify that new node was added to some graph
     // TODO: verify that new node is child of named parent
    Assertions.assertTrue(receivedNewNodeEvent);
  }

  // TODO: tests that we can't add new subnodes to nodes that are not graph nodes
  // TODO: tests that we can't add a node that already has a parent to a node.

  // TODO: tests we can copy a node into a child
  // TODO: Tests we can encapsulate a set of nodes into a new graph node that is child of the current parent of them


  public void nodeAdded(NodeAddedEvent nae) {
    receivedNewNodeEvent = true;
  }
}
