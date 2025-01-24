package com.alesgaroth.zuv.index;

import java.util.Set;

public class FakeCRDT implements CRDT<String> {
  FakeCRDT(){ 
  }
  public Set<String> elements() {
    return null;
  }
}
