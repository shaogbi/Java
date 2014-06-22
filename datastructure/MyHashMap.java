/* usage:
    MyHashMap myHashMap = new MyHashMap();
    myHashMap.add(13, 3);
    myHashMap.add(14, 1);
    myHashMap.add(13, 3);
    myHashMap.add("a", 1.28);
    myHashMap.add("b", 2);
    System.out.println(myHashMap.get(26));
    System.out.println(myHashMap.get(13));
    System.out.println(myHashMap.contains(15));
    myHashMap.remove(13);
    System.out.println(myHashMap.contains("a"));
    myHashMap.clear();
*/

package datastructure;

public class MyHashMap {
  private class Node {
    public Object key;
    public Object val;
    public Node next;
    public Node(Object key, Object val) {
      this.key = key;
      this.val = val;
      next = null;
    }
  }
  private Node[] nodes;
  private int size;
  private int count;
  public MyHashMap() {
    size = 16;
    nodes = new Node[size];
    count = 0;
  }
  private void resize() {
    int teSize = size;
    size <<= 1;
    Node[] newNodes = new Node[size];
    for(int i=0;i<teSize;i++) {
      Node e = nodes[i];
      nodes[i] = null;
      while(e != null) {
        Node te = e.next;
        int hash = getHash(e.key);
        e.next = newNodes[hash];
        newNodes[hash] = e;
        e = te;
      }
    }
    nodes = newNodes;
  }
  private int getHash(Object key) {
    return key.hashCode() & (size - 1);
  }
  public void clear() {
    for(int i=0;i<size;i++) {
      nodes[i] = null;
    }
    count = 0;
  }
  public boolean contains(Object key) {
    return get(key) != null;
  }
  public void remove(Object key) {
    int hash = getHash(key);
    Node pre = nodes[hash];
    if(pre == null) {
      return;
    }
    if(pre.key.equals(key)) {
      nodes[hash] = null;
      count--;
      return;
    }
    for(Node e=pre.next;e!=null;pre=e,e=e.next) {
      if(e.key.equals(key)) {
        pre.next = e.next;
        e = null;
        count--;
        return;
      }
    }
  }
  public void add(Object key, Object val) {
    int hash = getHash(key);
    for(Node e=nodes[hash];e!=null;e=e.next) {
      if(e.key.equals(key)) {
        e.val = val;
        return;
      }
    }
    Node te = nodes[hash];
    nodes[hash] = new Node(key, val);
    nodes[hash].next = te;
    count++;
    if(count == size) {
      resize();
    }
  }
  public Object get(Object key) {
    int hash = getHash(key);
    for(Node e=nodes[hash];e!=null;e=e.next) {
      if(e.key.equals(key)) {
        return e.val;
      }
    }
    return null;
  }
}
