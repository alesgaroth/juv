package com.alesgaroth.zuv.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;


import com.alesgaroth.zuv.index.CRDT;
import com.alesgaroth.zuv.index.OpCRDT;
import com.alesgaroth.zuv.index.Replica;

public class UDPSenderTest {
  @Test
  public void createSender() throws Exception {
    UDPSender one = new UDPSender(InetAddress.getByName("127.0.0.1"), 5054);
  }


  @Test
  public void sendBetweenEm()  throws Exception {
    UDPTransceiver ut1 = new UDPTransceiver(5054);
    UDPTransceiver ut2 = new UDPTransceiver(5055);
    OpCRDT crdt1,  crdt2;
    Replicator rep1, rep2;
    crdt1 = new OpCRDT("foo");
    crdt2 = new OpCRDT("bar");
    rep1 = new Replicator(crdt1, ut1);
    rep2 = new Replicator(crdt2, ut2);
    ut1.setReplicator(rep1);
    ut2.setReplicator(rep2);

    ut1.addReplica("bar", new UDPSender(InetAddress.getByName("127.0.0.1"),5055));
    ut2.addReplica("foo", new UDPSender(InetAddress.getByName("127.0.0.1"),5054));

    crdt2.add("/howdy/");
    crdt1.add("/hello/");
    ut2.receiveIfYouCan();

    assertTrue(crdt2.contains("/hello/"), "actual " + crdt2.elements());

  }
}
