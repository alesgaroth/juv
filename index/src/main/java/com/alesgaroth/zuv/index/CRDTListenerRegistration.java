package com.alesgaroth.zuv.index;

public interface CRDTListenerRegistration {
  void register(CRDT.CRDTListener l, String prefix) ;
}
