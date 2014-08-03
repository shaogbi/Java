package others.consistenthash;

import java.util.*;

public class VirtualNode {
  private String serverName;
  private Map<Object, Object> cache;
  public VirtualNode(String serverName) {
    this.serverName = serverName;
    cache = new HashMap<Object, Object>();
  }
  public void clearAllObjects() {
    cache.clear();
  }
  public String getServerName() {
    return serverName;
  }
  public Set<Object> getEntrySet() {
    return cache.keySet();
  }
  public Object get(Object key) {
    return cache.get(key);
  }
  public void put(Object key, Object value) {
    cache.put(key, value);
  }
}
