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
  Router  cl;

  @BeforeEach
  public void beforeEach() {
    fake = new FakeCRDT();
    cl = new Router();
    fake.setListener(cl);
    subndx = new Index("/foo/", fake, cl);
    ndx = new Index("/", fake, cl);
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
    assertTrue(fake.log.contains("added /bob/"));
    assertTrue(ndx.contains("bob"), " in " + ndx.elements());
  }

  @Test
  public void canAddToSubIndex() {
    subndx.add("bob");
    assertTrue(fake.log.contains("added /foo/bob/"));
    assertTrue(subndx.contains("bob"), ""  + subndx.cacheSet);
  }

  @Test
  public void canRemoveFromIndex() {
    ndx.remove("bob");
    assertTrue(fake.log.contains("removed /bob/"));
  }

  @Test
  public void canRemoveFromSubIndex() {
    subndx.remove("bob");
    assertTrue(fake.log.contains("removed /foo/bob/"));
  }


  @Test
  public void addThenRemoveLeavesEmpty() {
    ndx.add("bob");
    ndx.remove("bob");
    assertFalse(ndx.contains("bob"));
    assertTrue(fake.log.contains("added /bob/"));
    assertTrue(fake.log.contains("removed /bob/"));
  }

  @Test
  public void passesThroughContains() {
    fake.data.add("/bar/bob");
    // start with a non-empty index
    Index index = new Index("/bar/", fake, cl);
    assertTrue(index.contains("bob"));
  }

  @Test
  public void registersAsListenerToCRDT() {
    assertTrue(fake.log.contains("listen"));
  }

  @Test
  public void canAddSubIndexes() {
    Index second = ndx.addIndex("subindex/");
    fake.add("/subindex/bar");
    assertTrue(second.contains("bar"), "is bar in " + second.cacheSet + "? " + cl.listeners + "," + fake.log);
  }

  @Test
  public void canAddToSubIndexes() {
    Index second = ndx.addIndex("subndx/");
    second.add("baz");
    assertTrue(second.contains("baz"), "is baz in " + second.cacheSet + "? " + cl.listeners + "," + fake.log);
  }

  @Test
  public void subIndexPrefixesGetSlash() {
    Index second = ndx.addIndex("subndx");
    second.add("bat");
    assertTrue(second.contains("bat"), "is bat in " + second.cacheSet + "? " + cl.listeners + "," + fake.log);
  }

  @Test
  public void canAddRegisters() {
    Index reg = ndx.addRegister("reg");
    reg.add("one");
    assertTrue(reg.contains("one"), "is one in " + reg.cacheSet);
    reg.add("two");
    assertTrue(reg.contains("two"), "is two in " + reg.cacheSet);
    assertFalse(reg.contains("one"), "is one not in " + reg.cacheSet);
  }

  @Test
  public void canGetPath() {
    assertEquals(subndx.getPath(), "/foo/");
  }

  @Test
  public void rmIndexRmsChildren() {
    Index one = ndx.addIndex("one");
    Index two = one.addIndex("two");
    Index three = two.addIndex("three");
    assertTrue(fake.log.contains("added /one/two/three/"), "" + fake.log);
    assertTrue(fake.contains("/one/two/three/"), "looking for three " + fake.data);
    assertTrue(fake.contains("/one/two/"), "" + fake.data);
    ndx.remove("one");
    assertFalse(fake.contains("/one/two/three/"), "" + fake.data);
    assertFalse(fake.contains("/one/two/"), "" + fake.data);
  }

  /*
    algoIndex.addIndex(name);
    
    String path = algoIndex.getPath();
    String startPath = path + "/" + startName + "/ouput/" + output + "/";
    String endPath = path + "/" + endName + "/input/" + input + "/";
    Register startReg = algoIndex.getRegister(startPath);
    startReg.set(endPath); // escapes the slashes (/) since those aren't valid names.

    algoIndex.remove(name); // this would have a ripple effect... removing connections, any sub nodes
    	// it automatically removes any incoming, but the outgoing would still exist unless it
     	// went looking for them...
    
    Index node = algoIndex.getIndex(nodeName);
    Register implReg = node.getRegister("implementation");
    Register javaReg = implReg.addRegister("java");
    javaReg.add(javaFuncName); ... the JVM version deals with the lookups and setting the number of outputs and inputs
   
    Index node = algoIndex.get(nodeName);
    Register implReg = node.getRegister("implementation");
    Register algoReg = implReg.addRegister("algorithm");
    algoReg.add(algoPath);
	       
   */
}
