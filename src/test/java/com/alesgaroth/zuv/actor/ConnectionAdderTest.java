package com.alesgaroth.zuv.actor;

import com.alesgaroth.zuv.GraphMismatchException;
import com.alesgaroth.zuv.InvalidConnectionException;
import com.alesgaroth.zuv.ZGraphNode;
import com.alesgaroth.zuv.ZNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ConnectionAdderTest {

  @Test
  public void aNodeWithNoInputsCannotBeConnected() {
    ConnectionAdder adder = new ConnectionAdder();
    ZNode node1 = new ZNode(0, 1);
    ZNode node2 = new ZNode(0, 0);

    Assertions.assertThrows( InvalidConnectionException.class, 
      () -> adder.connect(node1, 0, node2, 0));
  }

  @Test
  public void NodesInDifferentGraphsCannotBeDirectlyConnected() {
    ConnectionAdder adder = new ConnectionAdder();
    ZGraphNode node1 = new ZGraphNode(0, 1);
    ZNode node2 = new ZNode(1, 0);
    node1.addNode(node2);
    Assertions.assertThrows(GraphMismatchException.class,
      () -> adder.connect(node1, 0, node2, 0));
  }
}
