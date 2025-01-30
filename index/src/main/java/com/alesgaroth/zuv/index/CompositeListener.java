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

  public static String [] splitPathBase(String e) {
    String[] split = new String[2];
    if (e != null) {
      if (e.equals("/")) {
        split[0] =  "/";
        split[1] = null;
      } else if (e.charAt(e.length() - 1) != '/') {
        split[0] = e.substring(0, e.lastIndexOf('/') + 1);
        split[1] = e.substring(e.lastIndexOf('/', e.length() - 2) + 1);
      } else {
        split[0] = e.substring(0, e.lastIndexOf('/', e.length() - 2) + 1);
        split[1] = e.substring(e.lastIndexOf('/', e.length() - 2) + 1, e.length() - 1);
      }
    } else {
      split[0] = null;
      split[1] = null;
    }
    return split;
  }

  public static String getPrefixFrom(String e) {
    String[] split = splitPathBase(e);
    return split[0];
  }


  public void added(String e) {
    String [] split =  splitPathBase(e);
    listeners.get(split[0]).added(split[1]);
  }
  public void removed(String e) {
    String [] split =  splitPathBase(e);
    listeners.get(split[0]).removed(split[1]);
  }
}
