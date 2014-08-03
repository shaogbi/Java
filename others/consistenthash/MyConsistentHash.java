package others.consistenthash;

import java.util.*;
import java.util.Map.*;

/*
 * each Server is consist of several virtual nodes
 * objects are stored in virtual node
*/
public class MyConsistentHash {
  private Map<String, Server> serverMap;
  private TreeMap<Integer, VirtualNode> nodesMap;
  private final int numOfReplicas; // how many virtual nodes each Server have
  public MyConsistentHash(int numOfReplicas) {
    serverMap = new HashMap<String, Server>();
    nodesMap = new TreeMap<Integer, VirtualNode>();
    this.numOfReplicas = numOfReplicas;
  }
  @SuppressWarnings("unchecked")
  private void moveObjects(VirtualNode from, VirtualNode to) {
    Set<Object> fromEntrySet = from.getEntrySet();
    Iterator<Object> iterator = fromEntrySet.iterator();
    while(iterator.hasNext()) {
      Entry<Object, Object> entry = (Entry<Object, Object>)iterator.next();
      to.put(entry.getKey(), entry.getValue());
    }
  }
  private VirtualNode getVirtualNode(Object key) {
    if(nodesMap.isEmpty()) {
      System.out.println("There is no server in the Cluster, add Server first.");
      return null;
    }
    return getCeilingNode(getHash(key));
  }
  private VirtualNode getVirtualNodeByHash(Integer hash) {
    if(hash == null) {
      hash = nodesMap.firstKey();
    }
    return nodesMap.get(hash);
  }
  private VirtualNode getCeilingNode(int hash) {
    return getVirtualNodeByHash(nodesMap.ceilingKey(hash));
  }
  private VirtualNode getFloorNode(int hash) {
    return getVirtualNodeByHash(nodesMap.floorKey(hash));
  }
  private int getHash(Object key) { // TODO: replace hash function here
    return key.hashCode();
  }
  public Set<Object> getKeysByServerName(String name) { // return all keys stored on the specific Server
    Server server = serverMap.get(name);
    if(server == null) {
      return null;
    }
    Set<Object> keys = new HashSet<Object>();
    Set<Integer> nodes = server.getNodes();
    for(Integer nodeHash : nodes) {
      keys.addAll(nodesMap.get(nodeHash).getEntrySet());
    }
    return keys;
  }
  public int getServerCount() {
    return serverMap.size();
  }
  public void removeServer(String name) {
    if(!serverMap.containsKey(name)) {
      return;
    }
    if(getServerCount() == 1) {
      System.out.println("There is only one server in the Cluster, all objects will be cleared.");
      for(int i=0;i<numOfReplicas;i++) {
        int hash = getHash(name) + i;
        nodesMap.get(hash).clearAllObjects();
      }
      nodesMap.clear();
      serverMap.clear();
    } else {
      for(int i=0;i<numOfReplicas;i++) {
        int hash = getHash(name) + i;
        VirtualNode from = getFloorNode(hash);
        VirtualNode to = getCeilingNode(hash);
        moveObjects(from, to);
        nodesMap.remove(hash);
      }
      serverMap.remove(name);
    }
  }
  public void addServer(String name) {
    Server server = new Server(name);
    for(int i=0;i<numOfReplicas;i++) {
      int hash = getHash(name) + i;
      VirtualNode node = new VirtualNode(name);
      VirtualNode from = getFloorNode(hash);
      moveObjects(from, node);
      nodesMap.put(hash, node);
      server.addVirtualNode(hash);
    }
    serverMap.put(name, server);
  }
  public Object getCacheObject(Object key) {
    VirtualNode node = getVirtualNode(key);
    return node == null ? null : node.get(key);
  }
  public void putCacheObject(Object key, Object value) {
    VirtualNode node = getVirtualNode(key);
    if(node != null) {
      node.put(key, value);
    }
  }
}
