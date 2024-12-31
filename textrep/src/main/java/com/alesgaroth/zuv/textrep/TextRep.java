package com.alesgaroth.zuv.textrep;

import java.util.HashMap;
import java.util.Map;

import com.alesgaroth.zuv.design.Algorithm;
import com.alesgaroth.zuv.design.Func;

public class TextRep {

  Algorithm algo;
  Map<String, Func> byName = new HashMap<>();


  public TextRep(Algorithm algo) {
    this.algo = algo;
  }

  public TextRep modify(String commands) {
    var lines = commands.split("\n");
    for(var line: lines) {
      oneLine(line);
    }
    return this;
  }
  private TextRep oneLine(String commands) {
    var parts = commands.split(" ");
    switch (parts[0]) {
      case "CreateNode":
        createNode(parts[1]);
        break;
      case "Connect":
        connectNodes(parts[1], parts[2], parts[3], parts[4]);
        break;
      case "":
        break;
      default:
        throw new RuntimeException("Unimplemented command '" + parts[0] + "'");
    }
    return this;
  }

  private void createNode(String name) {
    Func f = new Func(0, 0);
    byName.put(name, f);
    algo.add(f);
  }

  private void connectNodes(String startName, String output, String endName, String input) {
    Func start = byName.get(startName);
    if (start == null) {
      throw new NullPointerException("haven't seen " + startName + " before " + byName.keySet());
    }
    Func end = byName.get(endName);
    if (end == null) {
      throw new NullPointerException("haven't seen " + end + " before " + byName.keySet());
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
}
