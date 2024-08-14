package com.alesgaroth.zuv.design;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class NodeTest 
{

    Node one = new Node(0, 1);
    Node two = new Node(1, 0);
    @Test
    public void canCreateConnectThem() {
        assertTrue(one != two);
        two.dependOn(0, one, 0);
    }


    @Test
    public void cantConnectToNonExistantOutput() {
      badConnection(0, 5);
    }

    @Test
    public void cantConnectToNegativeOutput() {
      badConnection(0, -1);
    }

    @Test
    public void cantConnectToNegativeInput() {
      badConnection(-1, 0);
    }

    @Test
    public void cantConnectToNonExistantInput() {
      badConnection(5, 0);
    }

    @Test
    public void canGetFirstOutput() {
      assertNotNull(one.getOutput(0));
    }

    @Test
    public void cantGetNonExistantOutput() {
      missingOutput(1);
      missingOutput(-1);
    }

    @Test
    public void canGetOtherEndOfOutput() {
      two.dependOn(0, one, 0);

      Connection c = one.getOutput(0); 
      Iterator<Connection.NodePort> it = c.getListeners().iterator();
      assertEquals(it.next(), new Connection.NodePort(two, 0));
    }


    void badConnection(int input, int output) {
      assertThrows(Node.BadConnectionException.class,
        () -> two.dependOn(input, one, output));
    }

    void missingOutput(int output) {
      assertThrows(Node.BadConnectionException.class,
        () -> one.getOutput(output));
    }

}
