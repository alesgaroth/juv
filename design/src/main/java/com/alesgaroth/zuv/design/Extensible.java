package com.alesgaroth.zuv.design;

import java.util.HashMap;
import java.util.Map;

public class Extensible {
  public interface Extension {}

  private Map<Class<?extends Extension>, Extension> extensions = new HashMap<>();

  public final void extendWith(Extension obj) {
    extensions.put(obj.getClass(), obj);
  }
  public final <T extends Extension> T getExtension(Class<? extends T> clz) {
    return (T)extensions.get(clz);
  }
  public final void removeExtension(Extension ex) {
    extensions.remove(ex.getClass());
  }
}
