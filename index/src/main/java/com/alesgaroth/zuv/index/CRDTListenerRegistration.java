package com.alesgaroth.zuv.index;

public interface CRDTListenerRegistration {
  void register(CRDT.CRDTListener<String> l, String prefix) ;
}
