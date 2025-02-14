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
    // 
    // algoIndex.addIndex(name);
  }

  private void connectNodes(String startName, String output, String endName, String input) {
    /*
    String path = algoIndex.getPath();
    String startPath = path + "/" + startName + "/ouput/" + output + "/";
    String endPath = path + "/" + endName + "/input/" + input + "/";
    Register startReg = algoIndex.getRegister(startPath);
    startReg.set(endPath); // escapes the slashes (/) since those aren't valid names.
    */
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
    // algoIndex.remove(name); // this would have a ripple effect... removing connections, any sub nodes
    // 	// it automatically removes any incoming, but the outgoing would still exist unless it
    // 	// went looking for them...
    Func f = algo.getByName(name);
    if (f == null) {
      return;
    }
    algo = algo.remove(f); // also removes any links
  }

  private void setJavaFunc(String nodeName, String javaFuncName) {
    //Index node = algoIndex.getIndex(nodeName);
    //Register implReg = node.getRegister("implementation");
    //Register javaReg = implReg.addRegister("java");
    //javaReg.add(javaFuncName); ... the JVM version deals with the lookups and setting the number of outputs and inputs
  }

  private void setAlgorithm(String nodeName, String algoPath ) {
    //Index node = algoIndex.get(nodeName);
    //Register implReg = node.getRegister("implementation");
    //Register algoReg = implReg.addRegister("algorithm");
    //algoReg.add(algoPath);
  }
 
}
