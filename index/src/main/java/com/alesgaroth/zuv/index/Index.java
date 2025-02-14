package com.alesgaroth.zuv.index;

import java.util.HashSet;
import java.util.Set;

public class Index {
  CRDT<String> crdt;
  String prefix;
  Set<String> cacheSet = new HashSet<>();

  public Index(String prefix, CRDT<String> crdt) {
    this.crdt = crdt;
    if (prefix.charAt(prefix.length() - 1) != '/') {
      this.prefix = prefix  + '/';
    } else {
      this.prefix = prefix;
    }
    int j = 0;
    for (String e: crdt.elements()) {
      String k = strip(e);
      cacheSet.add(k);
      j += 1;
    }
  }
  public Set<String> elements() {
    return cacheSet;
  }
  public boolean contains(String name) {
    return cacheSet.contains(name);
  }
  public void add(String e) {
    crdt.add(prefix + e);
  }


  public void remove(String e) {
    crdt.remove(prefix + e);
  }

  // an index and an note and a register are all implemented the same
  // but conceptually they're different, so different methods to add
  // them
  public Index addIndex(String e) {
    this.add(e);
    return new Index(prefix + e, crdt);
  }


  String strip(String e) {
    if (e.length() > prefix.length()
        && e.substring(0, prefix.length()).equals(prefix)) {
      return e.substring(prefix.length());
    } else {
      return e;
    }
  }

  class Listener implements CRDT.CRDTListener<String> {
    public void added(String e){ 
      String k = strip(e);
      cacheSet.add(k);
    }
    public void removed(String e){ 
      String k = strip(e);
      cacheSet.remove(k);
    }
  }
}
