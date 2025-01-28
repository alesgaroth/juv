package com.alesgaroth.zuv.index;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CompositeListener implements CRDT.CRDTListener<String> {
  Map<String, CRDT.CRDTListener<String>> listeners = new HashMap<>();
  public CompositeListener(CRDT<String> crdt) {
  }

  public void register(CRDT.CRDTListener<String> l, String prefix) {
    listeners.put(prefix, l);
  }

  public static String getPrefixFrom(String e) {
    if (e != null) {
      if (e.equals("/")) {
        return "/";
      }
      if (e.charAt(e.length() - 1) != '/') {
        return e.substring(0, e.lastIndexOf('/') + 1);
      } else {
        return e.substring(0, e.lastIndexOf('/', e.length() - 2) + 1);
      }
    }
    return e;
  }


  public void added(String e) {
    listeners.get(getPrefixFrom(e)).added(e);
  }
  public void removed(String e) {
    listeners.get(getPrefixFrom(e)).removed(e);
  }
}
