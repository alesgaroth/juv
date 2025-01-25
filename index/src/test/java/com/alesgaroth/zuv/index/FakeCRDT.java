package com.alesgaroth.zuv.index;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FakeCRDT implements CRDT<String> {
  public ArrayList<String> log = new ArrayList<>();
  public Set<String> data = new HashSet<>();

  FakeCRDT(){ 
  }
  public Set<String> elements() {
    return data;
  }
  public boolean contains(String e) {
    return data.contains(e);
  }
  public void add(String e) {
    log.add("added " + e);
  }
  public void remove(String e) {
    log.add("removed " + e);
  }
}
