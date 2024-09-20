package com.alesgaroth.zuv.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import com.alesgaroth.zuv.design.Node;

public class NodeInstanceTest {
    @Test
    public void canInstantiate1() {
        Node n = new Node(0, 0);
        NodeInstance ni = new NodeInstance(n);
        assertEquals(ni.getNode(), n);
        assertThrows(Node.BadConnectionException.class, () -> ni.getOutput(-1));
        assertThrows(Node.BadConnectionException.class, () -> ni.getOutput(1));
        assertThrows(Node.BadConnectionException.class, () -> ni.getInput(-1));
        assertThrows(Node.BadConnectionException.class, () -> ni.getInput(1));
        assertThrows(Node.BadConnectionException.class, () -> ni.setInput(null, 1));
        assertThrows(Node.BadConnectionException.class, () -> ni.setInput(null, -1));
    }
}
