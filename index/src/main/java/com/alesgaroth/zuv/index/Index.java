package com.alesgaroth.zuv.index;

import java.util.HashSet;
import java.util.Set;

public class Index {
  CRDT<String> crdt;
  String prefix;
  Set<String> cacheSet = new HashSet<>();
  CRDTListenerRegistration clr;

  public Index(String prefix, CRDT<String> crdt, CRDTListenerRegistration cl) {
    this.crdt = crdt;
    this.clr = cl;
    this.prefix = appendSlash(prefix);
    cl.register(new Listener(), this.prefix);
    int j = 0;
    for (String e: crdt.elements()) {
      String k = strip(e);
      cacheSet.add(k);
      j += 1;
    }
  }

  static public String appendSlash(String e) {
    if (e.charAt(e.length() - 1) != '/') {
      return e  + '/';
    } else {
      return e;
    }
  }
  public Set<String> elements() {
    return cacheSet;
  }
  public boolean contains(String name) {
    return cacheSet.contains(name);
  }
  public void add(String e) {
    crdt.add(prefix + appendSlash(e));
  }


  public void remove(String e) {
    String pref = prefix + appendSlash(e);
    for (String elem : new HashSet<String>(crdt.elements())){
      if (elem.startsWith(pref)) {
        crdt.remove(elem);
      }
    }
    crdt.remove(pref);
  }

  // an index and a tag and a register are all implemented the same
  // but conceptually they're different, so different methods to add
  // them
  public Index addIndex(String e) {
    this.add(e);
    return new Index(prefix + e, crdt, clr);
  }

  public Register addRegister(String e) {
    this.add(e);
    return new Register(prefix + e, crdt, clr);
  }

  public String getPath() {
    return prefix;
  }


  String strip(String e) {
    if (e.length() > prefix.length()
        && e.substring(0, prefix.length()).equals(prefix)) {
      return e.substring(prefix.length());
    } else {
      return e;
    }
  }

  static class Register extends Index {
    Register(String prefix, CRDT<String> crdt, CRDTListenerRegistration cl) {
      super(prefix, crdt, cl);
    }

    public void add(String e) {
      Set<String> elems = new HashSet<>(elements());
      for(String elem: elems) {
        super.remove(elem);
      }
      super.add(e);
    }
  }

  class Listener implements CRDT.CRDTListener<String> {
    public void added(String e){ 
      String k = strip(e);
      cacheSet.add(k);
    }
    public void removed(String e){ 
      String k = strip(e);
      // TODO:  to get a remove wins style we need to
      // remove again here in a delayed thread
      cacheSet.remove(k);
    }
  }
}
