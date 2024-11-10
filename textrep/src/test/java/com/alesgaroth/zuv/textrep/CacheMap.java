package com.alesgaroth.zuv.textrep;

public class CacheMap {
  static Algorithm cache = new Algorithm();
  public static Algorithm get(String name) {
    return cache;
  }
}
