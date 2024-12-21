package com.alesgaroth.zuv.design;

import java.util.HashSet;
import java.util.List;
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
   * We have some information that makes it easier than quasi-polynomial time
   * 
   */
  public boolean equivalentTo(Algorithm other) {
    if (other == this) return true;
    if (other.numFuncs() != this.numFuncs()) return false;
    if (this.countEdges() != other.countEdges()) return false;
    if (this.countRoots() != other.countRoots()) return false;
    if (this.countLeaves() != other.countLeaves()) return false;
    List<Set<Func>> columns = new GraphOrder(this).ordered();
    List<Set<Func>> oColumns = new GraphOrder(other).ordered();
    if (columns.size() != oColumns.size()) return false;
    if (!columnsHaveTheSameNumberEach(columns, oColumns)) return false;
    if (canMatchFuncs(columns, oColumns)) return true;
    throw new NotYetImplemented("Graph is too complicated");
  }

  public String howDiff(Algorithm other) {
    if (other.numFuncs() != this.numFuncs()) return "Different Number Funcs " + other.numFuncs() + " != " + this.numFuncs() ;
    if (this.countEdges() != other.countEdges()) return "Different Number Edges";
    if (this.countRoots() != other.countRoots()) return "Different Number Roots";
    if (this.countLeaves() != other.countLeaves()) return "Different Number Leaves";

    List<Set<Func>> columns = new GraphOrder(this).ordered();
    List<Set<Func>> oColumns = new GraphOrder(other).ordered();

    if (columns.size() != oColumns.size()) return "Different number columns";
    if (!columnsHaveTheSameNumberEach(columns, oColumns)) return "Different number in columns";
    throw new NotYetImplemented("Graph is too complicated");
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
