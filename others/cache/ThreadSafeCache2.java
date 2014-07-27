package others.cache;

import java.util.concurrent.*;

// use ConcurrentHashMap to reduce lock possibility, lazy loading, better performance
public class ThreadSafeCache2 {
  private ConcurrentHashMap<String, FutureTask<CacheObject>> cache = null;
  public ThreadSafeCache2() {
    cache = new ConcurrentHashMap<String, FutureTask<CacheObject>>();
  }
  class CreateCacheObjectTask implements Callable<CacheObject> {
    private String name;
    public CreateCacheObjectTask(String name) {
      super();
      this.name = name;
    }
    @Override
    public CacheObject call() throws Exception {
      return new CacheObject(name);
    }
  }
  public void remove(String key) {
    cache.remove(key);
  } 
  public CacheObject get(String key) throws InterruptedException, ExecutionException {
    FutureTask<CacheObject> ft = cache.get(key);
    if(ft != null) {
      return ft.get();
    }
    FutureTask<CacheObject> ft2 = new FutureTask<CacheObject>(new CreateCacheObjectTask(key));
    FutureTask<CacheObject> val = cache.putIfAbsent(key, ft2);
    if(val == null) {
      val = ft2; // or "val = cache.get(key)";
      val.run();
    }
    return val.get();
  }
}
