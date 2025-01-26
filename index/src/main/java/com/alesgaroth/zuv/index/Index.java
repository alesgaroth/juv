package com.alesgaroth.zuv.index;

import java.util.HashSet;
import java.util.Set;

public class Index implements CRDT<String> {
  CRDT<String> crdt;
  String prefix;
  Set<String> cacheSet = new HashSet<>();

  public Index(String prefix, CRDT<String> crdt) {
    this.crdt = crdt;
    this.prefix = prefix;
    crdt.setListener(new Listener());
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

  public void setListener(CRDTListener<String> l) {
  }

  String strip(String e) {
    return e.substring(prefix.length());
  }

  private class Listener implements CRDTListener<String> {
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
