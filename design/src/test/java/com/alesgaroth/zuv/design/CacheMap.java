package com.alesgaroth.zuv.design;

import java.util.HashMap;
import java.util.Map;

import com.alesgaroth.zuv.index.Index;


public class CacheMap {
  static Map<String, Algorithm> cache = new HashMap<>();
  public static Algorithm get(String name, Index parent) {
    if (!cache.containsKey(name)) {
      Algorithm algo;
      switch(name) {
        case "empty":
                  algo = empty(parent);
                  break;
        case "oneNode":
                  algo = oneNode(parent);
                  break;
        case "twoConnectedNodes":
                  algo = twoConnectedNodes(parent);
                  break;
        default: throw new NotYetImplemented("sorry, don't know " + name);
      }
      cache.put(name, algo);
    }
    return cache.get(name);
  }

  static Algorithm empty(Index ndx) {
    return new Algorithm(ndx);
  }

  static Algorithm oneNode(Index parent) {
    Algorithm algo = new Algorithm(parent);
    Func one = new Func("foo", 0, 0, parent);
    algo.add(one);
    return algo;
  }
  static Algorithm twoConnectedNodes(Index parent) {
    Algorithm algo = new Algorithm(parent);
    Func one = new Func("foo", 0, 1, parent);
    Func two = new Func("bar", 1, 0, parent);
    two.dependOn(0, one, 0);
    algo.add(one);
    algo.add(two);
    return algo;
  }
}
