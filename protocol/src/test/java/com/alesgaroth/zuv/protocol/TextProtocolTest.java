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

public class TextProtocolTest {

  OpCRDT crdt;
  MockTextSender ts;
  Replicator rep;

  @BeforeEach
  public void before() {
    crdt = new OpCRDT("foo");
    ts = new MockTextSender();
    rep = new Replicator(crdt, ts);
  }

  @Test
  public void testAdded() {
    crdt.add("/hello/");
    assertLastIs("added /hello/ foo:1", "");
  }

  @Test
  public void testRemoved() {
    crdt.add("/hello/");
    crdt.remove("/hello/");
    assertLastIs("removed /hello/ foo:1", "");
  }

  @Test
  public void testReadAdded() {
    rep.receive("added /howdy/ bar:1");
    assertTrue(crdt.contains("/howdy/"), "actual " + crdt.elements());
  }

  @Test
  public void testReadRemoved() {
    crdt.add("/hola/");
    assertTrue(crdt.contains("/hola/"), "actual " + crdt.elements());
    rep.receive("removed /hola/ foo:1");
    assertFalse(crdt.contains("/hola/"), "actual " + crdt.elements());
  }

  private void assertLastIs(String expected, String extra) {
    assertEquals(expected, ts.log.get(ts.log.size()-1), "actual " + ts.log + extra);
  }

  //  Can create peer -- that's OpCRDT's Replica
  //  can get updates when index is edited -- that's OpCRDT's Replica
  //  can delivery updates when peer is edited -- that's OpCRDT's Replica
  //  text format:
  //  	added /path/to/index/ uniquekey oldkey oldkey2\n
  //  	removed /path/to/index/ oldkey oldkey2\n

  public class MockTextSender implements TextSender {
    ArrayList<String> log = new ArrayList<>();
    public void send(String s) {
      log.add(s);
    }
  }
 
}
