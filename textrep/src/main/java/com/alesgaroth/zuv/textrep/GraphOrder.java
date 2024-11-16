package com.alesgaroth.zuv.textrep;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
  List<Set<Func>> ordered() {
    Set<Func> col = new HashSet<>();
    for(Func root: algo.getRoots()) {
      root.extendWith(new FuncExtension());
      col.add(root);
    }
    int max = algo.numFuncs();
    int colNo = 0;
    for(colNo = 0; colNo < max && !col.isEmpty(); colNo += 1) {
      Set<Func> nextCol = new HashSet<>();
      for(Func func: col) {
        FuncExtension fe = func.getExtension(FuncExtension.class);
        fe.column = colNo;
        for(int j = 0; j < func.getNumOutputs(); j += 1) {
          for(FuncPort fp: func.getOutput(j).getListeners()) {
            Func next = fp.func();
            FuncExtension fen = next.getExtension(FuncExtension.class);
            if (fen == null) {
              fen = new FuncExtension();
              next.extendWith(fen);
            }
            fen.column = colNo + 1;
            nextCol.add(next);
          }
        }
      }
      col = nextCol;
    }

    
    List<Set<Func>> list =  new ArrayList<Set<Func>>();
    col = new HashSet<>(algo.getRoots());
    if (col.isEmpty()) {
      return list;
    }
    list.add(col);
    for(int k = 1; k <= colNo && !col.isEmpty(); k += 1) {
      Set<Func> nextCol = new HashSet<>();
      for(Func func: col) {
        for (int j = 0; j < func.getNumOutputs(); j += 1) {
          for (FuncPort fp: func.getOutput(j).getListeners()) {
            Func next = fp.func();
            FuncExtension fen = next.getExtension(FuncExtension.class);
            if (fen.column == k) {
              nextCol.add(next);
            }
          }
        }
      }
      if (nextCol.isEmpty()) {
        return list;
      }
      list.add(nextCol);
      col = nextCol;
    }
    return list;
  }

  class FuncExtension implements Func.Extension {
    int column;
  }
}
