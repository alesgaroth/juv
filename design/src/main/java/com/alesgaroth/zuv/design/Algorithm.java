package com.alesgaroth.zuv.design;

import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alesgaroth.zuv.design.Func;
import com.alesgaroth.zuv.design.Value.FuncPort;

public class Algorithm {
  Map<String,Func> funcs = new HashMap<>();

  public Algorithm() {
  }

  private Algorithm(Set<Func> f) {
    this.funcs = f.stream().collect(Collectors.toMap(Func::getName, e -> e));
  }

  /**
   * In general graph equivalence (aka Graph Isomorphism) can be very expensive (quasi-polynomial time)
   * https://en.wikipedia.org/wiki/Graph_isomorphism_problem#State_of_the_art
   *
   * We have some information that makes it easier than quasi-polynomial time
   * 
   */
  public boolean equivalentTo(Algorithm other) {
    if (other == this) return true;
    if (other.numFuncs() != this.numFuncs()) return false;
    if (this.countEdges() != other.countEdges()) return false;
    if (this.countRoots() != other.countRoots()) return false;
    //if (this.countLeaves() != other.countLeaves()) return false;
    List<Set<Func>> columns = new GraphOrder(this).ordered();
    List<Set<Func>> oColumns = new GraphOrder(other).ordered();
    if (columns.size() != oColumns.size()) return false;
    if (!columnsHaveTheSameNumberEach(columns, oColumns)) return false;
    if (canMatchFuncs(columns, oColumns)) return true;
    throw new NotYetImplemented("Graph is too complicated");
  }

  public String howDiff(Algorithm other) {
    if (other.numFuncs() != this.numFuncs()) return "Different Number Funcs " + this.numFuncs() + " != " + other.numFuncs() ;
    if (this.countEdges() != other.countEdges()) return "Different Number Edges " + this.countEdges() + " != " + other.countEdges();
    if (this.countRoots() != other.countRoots()) return "Different Number Roots " + this.countRoots() + " != " + other.countRoots();
    //if (this.countLeaves() != other.countLeaves()) return "Different Number Leaves " + this.countLeaves() + " != " + other.countLeaves();

    List<Set<Func>> columns = new GraphOrder(this).ordered();
    List<Set<Func>> oColumns = new GraphOrder(other).ordered();

    if (columns.size() != oColumns.size()) return "Different number columns";
    if (!columnsHaveTheSameNumberEach(columns, oColumns)) return "Different number in columns";
    throw new NotYetImplemented("Graph is too complicated");
  }

  public String print() {
    String output = "";
    for(Func f: funcs.values()) {
      output += " " + f.getName();
    }
    return output;
  }

  public boolean canMatchFuncs(List<Set<Func>> columns, List<Set<Func>> oColumns) {
    for (int k = 0; k < columns.size(); k += 1) {
      Set<Func> cols = columns.get(k);
      if (cols.size() == 1) {
        continue;
      }
      // TODO
    }
    return true;
  }
  public boolean columnsHaveTheSameNumberEach(List<Set<Func>> columns, List<Set<Func>> oColumns) {
    for (int k = 0; k < columns.size(); k += 1) {
      Set<Func> col = columns.get(k);
      Set<Func> oCol = oColumns.get(k);
      if (col.size() != oCol.size()) {
        return false;
      }
    }
    return true;
  }

  public int numFuncs() {
    return funcs.size();
  }

  public int countEdges() {
    int count = 0;
    for(Func f: funcs.values()) {
      int num = f.getNumOutputs();
      for (int j = 0; j< num; j += 1) {
        count += f.getOutput(j).getListeners().size();
      }
    }
    return count;
  }

  public Set<Func> getRoots() {
    Set<Func> roots = new HashSet<>(funcs.values());
    for(Func f: funcs.values()) {
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
    for(Func f: funcs.values()) {
      int num = f.getNumOutputs();
      for (int j = 0; j< num; j += 1) {
        if(f.getOutput(j).getListeners().isEmpty()) {
          leaves += 1;
        }
      }
    }
    return leaves;
  }

  public void add(Func f) {
    funcs.put(f.getName(), f);
  }
  public Func getByName(String name) {
    return funcs.get(name);
  }
  public Set<String> names() {
    return funcs.keySet();
  }
  public Algorithm remove(Func f) {
    // are there any links pointing to f? 
    for(Func func: funcs.values()) {
      func.removeListenersTo(f);
    }

    // are there any links pointing from f? 
    for (int j = f.getNumOutputs()-1; j >= 0; j -= 1) {
      f.removeOutput(j);
    }
    funcs.remove(f.getName());
    return this;
  }

  static class FuncFactory implements AlgorithmCopier.ZNodeFactory<Func> {
    public Func createFunc(Func n){
      return n.shallowClone();
    }
  }

  public Algorithm shallowCopy() {
    return new Algorithm(new HashSet<>(new AlgorithmCopier(new FuncFactory()).instantiate(funcs.values())));
  }

}
