package com.alesgaroth.zuv.index;

import java.io.Serializable;
import java.util.Set;

public interface CRDT {
  Set<String> elements();
  boolean contains(String e);
  void add(String e);
  void remove(String e);

  void setListener(CRDTListener l);

  public interface CRDTListener {
    void added(String e);
    void removed(String e);
  }
}
