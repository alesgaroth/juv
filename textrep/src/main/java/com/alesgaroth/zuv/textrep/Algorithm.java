package com.alesgaroth.zuv.textrep;

import java.util.HashSet;
import java.util.Set;

import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.design.Value.FuncPort;

public class Algorithm {
  int numItems = 0;
  Set<Func> funcs = new HashSet<>();

  /**
   * In general graph equivalence (aka Graph Isomorphism) can be very expensive (quasi-polynomial time)
   * https://en.wikipedia.org/wiki/Graph_isomorphism_problem#State_of_the_art
   * 
   */
  public boolean equivalentTo(Algorithm other) {
    if (other == this) return true;
    if (other.funcs.size() != this.funcs.size()) return false;
    if (this.countEdges() != other.countEdges()) return false;
    if (this.countRoots() != other.countRoots()) return false;
    if (this.countLeaves() != other.countLeaves()) return false;
    return true;
  }

  public int countEdges() {
    int count = 0;
    for(Func f: funcs) {
      int num = f.getNumOutputs();
      for (int j = 0; j< num; j += 1) {
        count += f.getOutput(j).getListeners().size();
      }
    }
    return count;
  }

  public Set<Func> getRoots() {
    Set<Func> roots = new HashSet<>(funcs);
    for(Func f: funcs) {
      int num = f.getNumOutputs();
      for (int j = 0; j< num; j += 1) {
        for(FuncPort fp: f.getOutput(j).getListeners()){
          roots.remove(fp.func());
        }
      }
    }
    return roots;
  }

  public int countRoots() {
    return getRoots().size();
  }

  public int countLeaves() {
    int leaves = 0;
    for(Func f: funcs) {
      int num = f.getNumOutputs();
      for (int j = 0; j< num; j += 1) {
        if(f.getOutput(j).getListeners().size() == 0) {
          leaves += 1;
        }
      }
    }
    return leaves;
  }

  public void add(Func f) {
    funcs.add(f);
  }
}
