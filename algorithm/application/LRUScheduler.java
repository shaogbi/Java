/* usage:
    LRUScheduler myLRU = new LRUScheduler(3);
    System.out.println(myLRU.get(13));
    myLRU.put(1, "a");
    System.out.println(myLRU.get(1));
    myLRU.put(2, "b");
    myLRU.put(3, "c");
    System.out.println(myLRU.get(3));
    myLRU.put(4, "d");
    System.out.println(myLRU.get(1));
    myLRU.put(4, "four");
    System.out.println(myLRU.get(4));
    System.out.println(myLRU.get(2));
    myLRU.put(5, "five");
    System.out.println(myLRU.get(3));
    myLRU.put(4, "ss");
    myLRU.put(5, "aaa");
    System.out.println(myLRU.get(2));
    System.out.println(myLRU.get(4));
*/

package algorithm.application;

import java.util.*;

public class LRUScheduler {
  private class Node {
    public Object key;
    public Object val;
    private Node pre;
    private Node next;
    public Node(Object key, Object val) {
      this.key = key;
      this.val = val;
    }
  }
  private Map<Object, Node> nodesMap;
  private Node head;
  private Node tail;
  private int capacity;
  private int size;
  public LRUScheduler(int capacity) {
    head = null;
    tail = null;
    this.capacity = capacity;
    nodesMap = new HashMap<Object, Node>();
    size = 0;
  }
  private void removeTailNode() {
    nodesMap.remove(tail.key);
    tail = tail.pre;
    tail.next = null;
    size--;
  }
  private void addNewNode(Object key, Object val) {
    Node newNode = new Node(key, val);
    newNode.next = head;
    newNode.pre = null;
    if(head != null) {
      head.pre = newNode;
    }
    head = newNode;
    nodesMap.put(key, newNode);
    size++;
    if(size == 1) {
      tail = newNode;
    }
  }
  private void moveCurrToHead(Node curr) {
    if(head == curr) {
      return;
    }
    if(curr == tail) {
      tail = tail.pre;
    }
    if(curr.next != null) {
      curr.next.pre = curr.pre;
    }
    if(curr.pre != null) {
      curr.pre.next = curr.next;
    }
    curr.next = head;
    curr.pre = null;
    head.pre = curr;
    head = curr;
  }
  public Object get(Object key) {
    if(nodesMap.containsKey(key)) {
      Node curr = nodesMap.get(key);
      moveCurrToHead(curr);
      return curr.val;
    } else {
      return null;
    }
  }
  public void put(Object key, Object val) {
    if(nodesMap.containsKey(key)) {
      Node curr = nodesMap.get(key);
      moveCurrToHead(curr);
      curr.val = val;
    } else {
      addNewNode(key, val);
      if(size > capacity) {
        removeTailNode();
      }
    }
  }
}
