package concurrency.copyonwrite;

import java.util.*;

public class MyCopyOnWriteMap<K, V> {
  private volatile Map<K, V> map;
  public MyCopyOnWriteMap() {
    map = new HashMap<K, V>();
  }
  public MyCopyOnWriteMap(int initialCapacity) {
    map = new HashMap<K, V>(initialCapacity);
  }
  public void putAll(Map<? extends K, ? extends V> newData) {
    synchronized(this) {
      Map<K, V> newMap = new HashMap<K, V>(map);
      newMap.putAll(newData);
      map = newMap;
    }
  }
  public V put(K key, V value) {
    synchronized(this) {
      Map<K, V> newMap = new HashMap<K, V>(map);
      V val = newMap.put(key, value);
      map = newMap;
      return val;
    }
  }
  public V get(K key) {
    return map.get(key);
  }
}
