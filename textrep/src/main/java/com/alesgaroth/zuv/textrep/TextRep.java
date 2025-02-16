package com.alesgaroth.zuv.textrep;

import java.util.HashMap;
import java.util.Map;

import com.alesgaroth.zuv.design.Algorithm;
import com.alesgaroth.zuv.design.Func;

public class TextRep {

  AlgorithmEditor editor;


  public TextRep(Algorithm algo) {
    this.editor = new AlgorithmEditor(algo);
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
    editor.createNode(name);
    // 
    // algoIndex.addIndex(name);
  }

  private void connectNodes(String startName, String output, String endName, String input) {
    editor.connectNodes(startName, output, endName, input);
    /*
    String path = algoIndex.getPath();
    String startPath = path + "/" + startName + "/ouput/" + output + "/";
    String endPath = path + "/" + endName + "/input/" + input + "/";
    Register startReg = algoIndex.getRegister(startPath);
    startReg.set(endPath); // escapes the slashes (/) since those aren't valid names.
    */
  }

  private void deleteNode(String name) {
    editor.deleteNode(name);
    // algoIndex.remove(name); // this would have a ripple effect... removing connections, any sub nodes
    // 	// it automatically removes any incoming, but the outgoing would still exist unless it
    // 	// went looking for them...
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
