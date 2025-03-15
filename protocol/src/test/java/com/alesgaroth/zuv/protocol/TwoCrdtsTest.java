package com.alesgaroth.zuv.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

public class TwoCrdtsTest {
  OpCRDT crdt1,  crdt2;
  Replicator rep1, rep2;
  SimpleTextSender ts1, ts2;

  @BeforeEach
  public void before() {
    crdt1 = new OpCRDT("foo");
    crdt2 = new OpCRDT("bar");
    ts1 = new SimpleTextSender();
    ts2 = new SimpleTextSender();
    rep1 = new Replicator(crdt1, ts1);
    rep2 = new Replicator(crdt2, ts2);
    ts1.others.add(rep2);
    ts2.others.add(rep1);
  }


  @Test
  public void testAdded() {
    crdt1.add("/hello/");
    assertTrue(crdt2.contains("/hello/"), "actual " + crdt2.elements());
  }

  @Test
  public void testThree() {
    OpCRDT crdt3 = new OpCRDT("bar");
    SimpleTextSender ts3 = new SimpleTextSender();
    Replicator rep3 = new Replicator(crdt3, ts3);

    ts1.others.add(rep3);
    ts2.others.add(rep3);

    ts3.others.add(rep1);
    ts3.others.add(rep2);

    crdt1.add("/hello/");
    crdt2.add("/howdy/");
    crdt3.add("/gday/");

    assertTrue(crdt2.contains("/hello/"), "actual " + crdt2.elements());
    assertTrue(crdt3.contains("/hello/"), "actual " + crdt2.elements());
    assertTrue(crdt1.contains("/howdy/"), "actual " + crdt2.elements());
    assertTrue(crdt3.contains("/howdy/"), "actual " + crdt2.elements());
    assertTrue(crdt1.contains("/gday/"), "actual " + crdt2.elements());
    assertTrue(crdt2.contains("/gday/"), "actual " + crdt2.elements());
  }

  public class SimpleTextSender implements TextSender {
    ArrayList<Replicator> others = new ArrayList<>();
    public void send(String s) {
      for (Replicator other: others) {
        other.receive(s);
      }
    }
  }
 
}
