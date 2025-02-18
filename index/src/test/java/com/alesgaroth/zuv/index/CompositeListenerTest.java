package com.alesgaroth.zuv.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CompositeListenerTest {
  public static class SpyListener implements CRDT.CRDTListener<String> {
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
    Router cl = new Router();
    SpyListener spy = new SpyListener();
    cl.register(spy, "/");
    fake.setListener(cl);
    fake.add("/bob");
    assertTrue(spy.log.contains("added bob"));
    fake.remove("/bob");
    assertTrue(spy.log.contains("removed bob"));
  }

  @Test
  public void calcPrefix() {
    assertNull(Router.getPrefixFrom(null));
    assertEquals("/", Router.getPrefixFrom("/"));
    assertEquals("/", Router.getPrefixFrom("/bob"));
    assertEquals("/", Router.getPrefixFrom("/bob/"));
    assertEquals("/bob/", Router.getPrefixFrom("/bob/foo"));
    assertEquals("/bob/", Router.getPrefixFrom("/bob/foo/"));
  }
  @Test
  public void splitDegenerate() {
    String [] split = Router.splitPathBase(null);
    assertNull(split[0]);
    assertNull(split[1]);

    split = Router.splitPathBase("/");
    assertEquals("/", split[0]);
    assertNull(split[1]);
  }

  @Test
  public void split() {
    assertSplit("/bob", "/", "bob");
    assertSplit("/bob/foo", "/bob/", "foo");
    assertSplit("/bob/", "/", "bob");
    assertSplit("/bob/foo/", "/bob/", "foo");
  }
  private void assertSplit(String full, String path, String base) {
    String [] split = Router.splitPathBase(full);
    assertEquals(path, split[0]);
    assertEquals(base, split[1]);
  }


  @Test
  public void canAdd2Listeners() {
    CRDT fake = new FakeCRDT();
    Router cl = new Router();
    SpyListener spy = new SpyListener();
    SpyListener spy2 = new SpyListener();
    cl.register(spy, "/");
    cl.register(spy2, "/bob/");
    fake.setListener(cl);
    fake.add("/bob/");
    assertLastLog(spy, "added bob");
    fake.remove("/bob/");
    assertLastLog(spy, "removed bob");
    assertNotLastLog(spy2, "removed bob");
    fake.add("/bob/foo/");
    assertNotLastLog(spy, "added foo");
    assertLastLog(spy2, "added foo");
    fake.remove("/bob/foo/");
    assertNotLastLog(spy, "removed foo");
    assertLastLog(spy2, "removed foo");
  }
  private void assertLastLog(SpyListener spy, String expected) {
    String actual = spy.log.get(spy.log.size() - 1);
    assertEquals(expected, actual);
  }

  private void assertNotLastLog(SpyListener spy, String expected) {
    if (spy.log.size() == 0) {
      return;
    }
    String actual = spy.log.get(spy.log.size() - 1);
    assertNotEquals(expected, actual);
  }

  @Test
  public void badPathShouldNotThrow(){
    CRDT fake = new FakeCRDT();
    Router cl = new Router();
    fake.setListener(cl);
    fake.add("hello");
    fake.remove("hello");
  }


}
