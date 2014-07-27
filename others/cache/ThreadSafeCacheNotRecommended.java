package others.cache;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class ThreadSafeCacheNotRecommended {
  private ConcurrentHashMap<String, CacheObject> cache = null;
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  public ThreadSafeCacheNotRecommended() {
    cache = new ConcurrentHashMap<String, CacheObject>();
  }
  public void remove(String key) {
    try {
      lock.writeLock().lock();
      cache.remove(key);
    } finally {
      lock.writeLock().unlock();
    }
  }
  public CacheObject get(String key) {
    CacheObject obj = null;
    try {
      lock.readLock().lock();
      obj = cache.get(key);
      if(obj == null) {
        lock.readLock().unlock();
        lock.writeLock().lock();
        obj = cache.get(key);
        if(obj == null) {
          obj = new CacheObject(key);
          cache.put(key, obj);
        }
        lock.readLock().lock();
        lock.writeLock().unlock();
      }
    } catch(Exception e) {
      lock.writeLock().unlock();
    }
    lock.readLock().unlock();
    return obj;
  }
}
