package com.alesgaroth.zuv.index;

public interface Effect<Q> {
  void apply(Q rcvr);
}
