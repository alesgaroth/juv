package com.alesgaroth.zuv.textrep;

import java.util.HashMap;
import java.util.Map;

import com.alesgaroth.zuv.design.Func;

public class CacheMap {
  static Map<String, Algorithm> cache = new HashMap<>();
  public static Algorithm get(String name) {
    if (!cache.containsKey(name)) {
      Algorithm algo;
      switch(name) {
        case "empty":
                  algo = empty();
									break;
        case "oneNode":
									algo = oneNode();
									break;
        case "twoConnectedNodes":
									algo = twoConnectedNodes();
									break;
        default: throw new NotYetImplemented("sorry, don't know " + name);
      }
      cache.put(name, algo);
    }
    return cache.get(name);
  }

  static Algorithm empty() {
    return new Algorithm();
  }

  static Algorithm oneNode() {
    Algorithm algo = new Algorithm();
    Func one = new Func(0, 0);
    algo.add(one);
    return algo;
  }
  static Algorithm twoConnectedNodes() {
    Algorithm algo = new Algorithm();
    Func one = new Func(0, 1);
    Func two = new Func(1, 0);
    two.dependOn(0, one, 0);
    algo.add(one);
    algo.add(two);
    return algo;
  }
}
