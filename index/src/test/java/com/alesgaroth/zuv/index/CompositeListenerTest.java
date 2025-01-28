package com.alesgaroth.zuv.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CompositeListenerTest {
  public class SpyListener implements CRDT.CRDTListener<String> {
    public ArrayList<String> log = new ArrayList<>();
    public void added(String e) {
      log.add("added " + e);
    }
    public void removed(String e) {
      log.add("removed " + e);
    }
  }

  @Test
  public void canAddListener() {
    CRDT fake = new FakeCRDT();
    CompositeListener cl = new CompositeListener(fake);
    SpyListener spy = new SpyListener();
    cl.register(spy, "/");
    fake.setListener(cl);
    fake.add("/bob");
    assertTrue(spy.log.contains("added /bob"));
    fake.remove("/bob");
    assertTrue(spy.log.contains("removed /bob"));
  }

  @Test
  public void calcPrefix() {
     assertNull(CompositeListener.getPrefixFrom(null));
     assertEquals("/", CompositeListener.getPrefixFrom("/"));
     assertEquals("/", CompositeListener.getPrefixFrom("/bob"));
     assertEquals("/", CompositeListener.getPrefixFrom("/bob/"));
     assertEquals("/bob/", CompositeListener.getPrefixFrom("/bob/foo"));
     assertEquals("/bob/", CompositeListener.getPrefixFrom("/bob/foo/"));
  }

  @Test
  public void canAdd2Listeners() {
    CRDT fake = new FakeCRDT();
    CompositeListener cl = new CompositeListener(fake);
    SpyListener spy = new SpyListener();
    SpyListener spy2 = new SpyListener();
    cl.register(spy, "/");
    cl.register(spy2, "/bob/");
    fake.setListener(cl);
    fake.add("/bob/");
    assertTrue(spy.log.contains("added /bob/"));
    assertFalse(spy2.log.contains("added /bob/"));
    fake.remove("/bob/");
    assertTrue(spy.log.contains("removed /bob/"));
    assertFalse(spy2.log.contains("removed /bob/"));
    fake.add("/bob/foo/");
    assertTrue(spy2.log.contains("added /bob/foo/"));
    assertFalse(spy.log.contains("added /bob/foo/"));
    fake.remove("/bob/foo/");
    assertTrue(spy2.log.contains("removed /bob/foo/"));
    assertFalse(spy.log.contains("removed /bob/foo/"));
  }

}
