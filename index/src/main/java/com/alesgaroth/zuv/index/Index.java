package com.alesgaroth.zuv.index;

import java.util.HashSet;
import java.util.Set;

public class Index implements CRDT<String> {
  CRDT<String> crdt;
  String prefix;

  public Index(String prefix, CRDT<String> crdt) {
    this.crdt = crdt;
    this.prefix = prefix;
  }
  public Set<String> elements() {
    return new HashSet<>();
  }
  public boolean contains(String name) {
    return false;
  }
  public void add(String e) {
    crdt.add(prefix + e);
  }
  public void remove(String e) {
    crdt.remove(prefix + e);
  }
}
