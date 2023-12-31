package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZNode;
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
     Assertions.assertTrue(receivedNewNodeEvent);
  }


  public void nodeAdded(NodeAddedEvent nae) {
    receivedNewNodeEvent = true;
  }
}
