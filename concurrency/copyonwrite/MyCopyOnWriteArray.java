package concurrency.copyonwrite;

import java.util.Arrays;
import java.util.concurrent.locks.*;

public class MyCopyOnWriteArray {
  private Object[] objs;
  transient final ReentrantLock lock = new ReentrantLock();
  public MyCopyOnWriteArray() {
    objs = new Object[0];
  }
  public void add(Object o) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
      int len = objs.length;
      Object[] newObjs = Arrays.copyOf(objs, len + 1);
      newObjs[len] = o;
      objs = newObjs;
    } finally {
      lock.unlock();
    }
  }
  public Object get(int index) {
    return objs[index];
  }
}
