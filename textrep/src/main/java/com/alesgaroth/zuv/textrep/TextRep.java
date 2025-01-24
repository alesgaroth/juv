package com.alesgaroth.zuv.textrep;

import java.util.HashMap;
import java.util.Map;

import com.alesgaroth.zuv.design.Algorithm;
import com.alesgaroth.zuv.design.Func;

public class TextRep {

  Algorithm algo;


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
      case "DeleteNode":
        deleteNode(parts[1]);
        break;
      case "":
        break;
      default:
        throw new RuntimeException("Unimplemented command '" + parts[0] + "'");
    }
    return this;
  }

  private void createNode(String name) {
    Func f = new Func(name, 0, 0);
    algo.add(f);
  }

  private void connectNodes(String startName, String output, String endName, String input) {
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

  private void deleteNode(String name) {
    Func f = algo.getByName(name);
    if (f == null) {
      return;
    }
    algo = algo.remove(f); // also removes any links
  }
}
