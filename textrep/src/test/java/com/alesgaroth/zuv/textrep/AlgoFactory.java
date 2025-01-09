package com.alesgaroth.zuv.textrep;

import java.util.HashMap;
import java.util.Map;

import com.alesgaroth.zuv.design.Algorithm;
import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.design.NotYetImplemented;


public class AlgoFactory {
  public static Algorithm get(String name) {
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
    return algo;
  }

  static Algorithm empty() {
    return new Algorithm();
  }

  static Algorithm oneNode() {
    Algorithm algo = new Algorithm();
    Func one = new Func("foo", 0, 0);
    algo.add(one);
    return algo;
  }
  static Algorithm twoConnectedNodes() {
    Algorithm algo = new Algorithm();
    Func one = new Func("foo", 0, 1);
    Func two = new Func("bar", 1, 0);
    two.dependOn(0, one, 0);
    algo.add(one);
    algo.add(two);
    return algo;
  }
}
