package com.alesgaroth.zuv.index;

import java.util.ArrayList;
import java.util.Set;

public class FakeCRDT implements CRDT<String> {
  public ArrayList<String> log = new ArrayList<>();
  FakeCRDT(){ 
  }
  public Set<String> elements() {
    return null;
  }
  public boolean contains(String e) {
    return false;
  }
  public void add(String e) {
    log.add("added " + e);
  }
  public void remove(String e) {
    log.add("removed " + e);
  }
}
