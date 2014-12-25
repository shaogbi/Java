package others.cache;

import java.util.*;

// use synchronized, low performance
public class ThreadSafeCache1 {
  private Map<String, CacheObject> cache = null;
  public ThreadSafeCache1() {
    cache = new HashMap<String, CacheObject>();
  }
  public synchronized void remove(String key) {
    cache.remove(key);
  }
  public synchronized CacheObject get(String key) {
    CacheObject obj = cache.get(key);
    if(obj == null) {
      obj = new CacheObject(key);
      cache.put(key, obj);
    }
    return obj;
  }
}
