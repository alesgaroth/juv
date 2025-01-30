package com.alesgaroth.zuv.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class IndexTest {

  Index ndx;
  FakeCRDT fake;
  Index subndx;

  @BeforeEach
  public void beforeEach() {
    fake = new FakeCRDT();
    CompositeListener cl = new CompositeListener(fake);
    fake.setListener(cl);
    subndx = new Index("/foo/", fake);
    cl.register(subndx.new Listener(), "/foo/");
    ndx = new Index("/", fake);
    cl.register(ndx.new Listener(), "/");
  }

  @Test
  public void emptyIndexHasNoElements() {
    Set<String> elems = ndx.elements();
    assertTrue(elems.isEmpty());
  }

  @Test
  public void emptyIndexDoesNotContainAnything() {
    assertFalse(ndx.contains("bob"));
  }

  @Test
  public void canAddToIndex() {
    ndx.add("bob");
    assertTrue(fake.log.contains("added /bob"));
    assertTrue(ndx.contains("bob"), " in " + ndx.elements());
  }

  @Test
  public void canAddToSubIndex() {
    subndx.add("bob");
    assertTrue(fake.log.contains("added /foo/bob"));
    assertTrue(subndx.contains("bob"), ""  + subndx.cacheSet);
  }

  @Test
  public void canRemoveFromIndex() {
    ndx.remove("bob");
    assertTrue(fake.log.contains("removed /bob"));
  }

  @Test
  public void canRemoveFromSubIndex() {
    subndx.remove("bob");
    assertTrue(fake.log.contains("removed /foo/bob"));
  }


  @Test
  public void addThenRemoveLeavesEmpty() {
    ndx.add("bob");
    ndx.remove("bob");
    assertFalse(ndx.contains("bob"));
    assertTrue(fake.log.contains("added /bob"));
    assertTrue(fake.log.contains("removed /bob"));
  }

  @Test
  public void passesThroughContains() {
    fake.data.add("/bob");
    // start with a non-empty index
    Index index = new Index("/", fake);
    assertTrue(index.contains("bob"));
  }

  @Test
  public void registersAsListenerToCRDT() {
    assertTrue(fake.log.contains("listen"));
  }
}
