package designpattern.singleton;

/* this version also works
 * 1. lazy loading
 * 2. thread safe with blocking, low performance
public class MySingleton {
  private static MySingleton instance = null;
  private MySingleton() {}
  public static synchronized MySingleton getInstance() {
    if(instance == null) {
      instance = new MySingleton();
    }
    return instance;
  } 
}
*/

// 1. lazy loading
// 2. thread safe with non-blocking, high performance
public class MySingleton {
  private MySingleton() {}
  private static class MySingletonHolder {
    public final static MySingleton instance = new MySingleton();
  }
  public static MySingleton getInstance() {
    return MySingletonHolder.instance;
  } 
}