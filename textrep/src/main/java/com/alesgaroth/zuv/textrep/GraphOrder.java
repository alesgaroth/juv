package com.alesgaroth.zuv.textrep;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.design.Value;
import com.alesgaroth.zuv.design.Value.FuncPort;

public class GraphOrder {
  Algorithm algo;
  GraphOrder(Algorithm algo) {
    this.algo = algo;
  }


  /**
   * returns a list of columns (as sets) of Funcs
   */
  public List<Set<Func>> ordered() {
    return kahnOrdered();
  }

  List<Func> kahnsAlgorithm() {
    List<Func> semiOrderedOutput = new ArrayList<>(algo.numFuncs());
    Queue<Func> queue = new ArrayDeque<>();
    for(Func func: algo.getRoots()) {
      queue.offer(func);
      func.extendWith(new FuncExtension());
    }
    int currCol = 0;
    while(!queue.isEmpty()) {
      Func func = queue.poll();

      FuncExtension fe = func.getExtension(FuncExtension.class);
      fe.column = currCol;
      currCol += 1;

      semiOrderedOutput.add(func);
      for(int j = 0; j < func.getNumOutputs(); j += 1) {
        for(FuncPort fp: func.getOutput(j).getListeners()) {
          Func m = fp.func();
          FuncExtension me = m.getExtension(FuncExtension.class);
          if (me == null) {
            me = new FuncExtension();
            m.extendWith(me);
          }
          me.incomingEdges -= 1;
          if (me.incomingEdges < 1) {
            queue.offer(m);
          }
        }
      }

    }
    return semiOrderedOutput;
  }

  void countIncomingEdges() {
    Queue<Func> queue = new ArrayDeque<>();
    for(Func root: algo.getRoots()) {
      queue.offer(root);
    }

    while(!queue.isEmpty()) {
      Func func = queue.poll();
      FuncExtension fe = func.getExtension(FuncExtension.class);
      if (fe != null && fe.visited) {
        continue;
      } else if (fe == null) {
        fe = new FuncExtension();
        fe.incomingEdges = 0;
      }
      fe.visited = true;
      for(int j = 0; j < func.getNumOutputs(); j += 1) {
        for(FuncPort fp: func.getOutput(j).getListeners()) {
          Func m = fp.func();
          FuncExtension me = m.getExtension(FuncExtension.class);
          if (me == null) {
            me = new FuncExtension();
            me.incomingEdges = 0;
          }
          me.incomingEdges += 1;
        }
      }
    }
  }

  List<Set<Func>> kahnOrdered() {
    countIncomingEdges();
    List<Func> semiOrderedOutput = kahnsAlgorithm();

    List<Set<Func>> retval = new ArrayList<>();
    int firstNext = 0;
    int currCol = 0;
    Set<Func> columnGroup = null; 

    for(Func func: semiOrderedOutput) {
      if (currCol == firstNext) {
        firstNext = algo.numFuncs();
        columnGroup = new HashSet<>();
        retval.add(columnGroup);
      }
      currCol += 1;
      columnGroup.add(func);
      for(int j = 0; j < func.getNumOutputs(); j += 1) {
        for(FuncPort fp: func.getOutput(j).getListeners()) {
            Func next = fp.func();
            FuncExtension fen = next.getExtension(FuncExtension.class);
            if (fen == null) {
              throw new NotYetImplemented("missing extension");
            }
            if (fen.column < firstNext) {
              firstNext = fen.column;
            }
        }
      }
    }
    return retval;
  }

  class FuncExtension implements Func.Extension {
    int column;
    int incomingEdges;
    boolean visited;
  }
}
