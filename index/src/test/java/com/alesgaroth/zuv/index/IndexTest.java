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
    ndx = new Index("/", fake);
    subndx = new Index("/foo/", fake);
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
    assertEquals("added /bob", fake.log.get(0));
    //assertTrue(ndx.contains("bob"));
  }

  @Test
  public void canAddToSubIndex() {
    subndx.add("bob");
    assertEquals("added /foo/bob", fake.log.get(0));
    //assertTrue(ndx.contains("bob"));
  }

  @Test
  public void canRemoveFromIndex() {
    ndx.remove("bob");
    assertEquals("removed /bob", fake.log.get(0));
  }

  @Test
  public void canRemoveFromSubIndex() {
    subndx.remove("bob");
    assertEquals("removed /foo/bob", fake.log.get(0));
  }


  @Test
  public void addThenRemoveLeavesEmpty() {
    ndx.add("bob");
    ndx.remove("bob");
    assertFalse(ndx.contains("bob"));
    assertEquals("added /bob", fake.log.get(0));
    assertEquals("removed /bob", fake.log.get(1));
  }

  @Test
  public void passesThroughContains() {
    fake.data.add("/bob");
    assertTrue(ndx.contains("bob"));
    assertFalse(subndx.contains("bob"));
  }
}
