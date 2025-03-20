package com.alesgaroth.zuv.textrep;

import java.util.HashMap;
import java.util.Map;

import com.alesgaroth.zuv.design.Algorithm;
import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.design.NotYetImplemented;
import com.alesgaroth.zuv.index.Index;


public class AlgoFactory {
  public static Algorithm get(String name, Index ndx) {
    Algorithm algo;
    switch(name) {
      case "empty":
                algo = empty(ndx);
                break;
      case "oneNode":
                algo = oneNode(ndx);
                break;
      case "twoConnectedNodes":
                algo = twoConnectedNodes(ndx);
                break;
      default: throw new NotYetImplemented("sorry, don't know " + name);
    }
    return algo;
  }

  static Algorithm empty(Index ndx) {
    return new Algorithm(ndx);
  }

  static Algorithm oneNode(Index ndx) {
    Algorithm algo = new Algorithm(ndx);
    Func one = new Func("foo", 0, 0, ndx);
    algo.add(one);
    return algo;
  }
  static Algorithm twoConnectedNodes(Index ndx) {
    Algorithm algo = new Algorithm(ndx);
    Func one = new Func("foo", 0, 1, ndx);
    Func two = new Func("bar", 1, 0, ndx);
    two.dependOn(0, one, 0);
    algo.add(one);
    algo.add(two);
    return algo;
  }
}
