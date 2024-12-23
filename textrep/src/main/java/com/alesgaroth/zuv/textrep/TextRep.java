package com.alesgaroth.zuv.textrep;

import com.alesgaroth.zuv.design.Algorithm;
import com.alesgaroth.zuv.design.Func;

public class TextRep {

  Algorithm algo;
  public TextRep(Algorithm algo) {
    this.algo = algo;
  }

  public TextRep modify(String commands) {
    var parts = commands.split(" ");
    switch (parts[0]) {
      case "CreateNode":
        algo.add(new Func(0, 0));
        break;
    }
    return null;
  }
}
