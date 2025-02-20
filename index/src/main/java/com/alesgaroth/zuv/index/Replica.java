package com.alesgaroth.zuv.index;

public interface Replica<Q> {
  void changed(Effect<Q> eff);
}
