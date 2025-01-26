package com.alesgaroth.zuv.index;

import java.io.Serializable;
import java.util.Set;

public interface CRDT<E extends Serializable> {
  Set<E> elements();
  boolean contains(E e);
  void add(E e);
  void remove(E e);

  void setListener(CRDTListener<E> l);

  public interface CRDTListener<E extends Serializable> {
    void added(E e);
    void removed(E e);
  }
}
