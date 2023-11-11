package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddNoderTest {
  @Test
  void canAddANode() {
      AddNoder adder = new AddNoder();
      ZGraphNode g = new ZGraphNode(0, 0);
      ZNode node = adder.addNode(g);
      Assertions.assertNotNull(node);
      Assertions.assertEquals(g, node.parent());
  }
}
