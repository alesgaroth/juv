package com.alesgaroth.zuv.textrep;

import com.alesgaroth.zuv.design.Algorithm;
import com.alesgaroth.zuv.design.Func;

public class AlgorithmEditor {
  Algorithm algo;
  AlgorithmEditor(Algorithm algo) {
    this.algo = algo;
  }
  public void createNode(String name) {
    Func f = new Func(name, 0, 0, algo.getIndex());
    algo.add(f);
  }
  public void connectNodes(String startName, String output, String endName, String input) {
    Func start = algo.getByName(startName);
    if (start == null) {
      throw new NullPointerException("haven't seen " + startName + " before " + algo.names());
    }
    Func end = algo.getByName(endName);
    if (end == null) {
      throw new NullPointerException("haven't seen " + end + " before " + algo.names());
    }
    int out = Integer.valueOf(output);
    int in = Integer.valueOf(input);
    while (in >= end.getNumInputs()) {
      end.addInput();
    }
    while (out >= start.getNumOutputs()) {
      start.addOutput();
    }
    end.dependOn(in, start, out);
  }
  public void deleteNode(String name) {
    Func f = algo.getByName(name);
    if (f == null) {
      return;
    }
    algo = algo.remove(f); // also removes any links
  }
}
