package concurrency.thread.threadlocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// this is a very simple implementation, better implementation see http://stackoverflow.com/questions/1202444/how-is-javas-threadlocal-implemented-under-the-hood
public class MyNaiveThreadLocal<T> {
  private Map<Thread, T> map = new ConcurrentHashMap<Thread, T>();
  public MyNaiveThreadLocal() {}
  protected T initialValue() {
    return null;
  }
  public T get() {
    Thread t = Thread.currentThread();
    T obj = map.get(t);
    if(obj == null) {
      obj = initialValue();
      map.put(t, obj);
    }
    return obj;
  }
  public void set(T value) {
    map.put(Thread.currentThread(), value);
  }
  public void remove() {
    map.remove(Thread.currentThread());
  }
}
