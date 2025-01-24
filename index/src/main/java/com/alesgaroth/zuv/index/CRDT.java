package com.alesgaroth.zuv.index;

import java.io.Serializable;
import java.util.Set;

public interface CRDT<E extends Serializable> {
  Set<E> elements();
}
