/* usage:
    MyHeap heap = new MyHeap(); // it is a min heap
    heap.insert(18);
    heap.insert(26);
    heap.insert(35);
    System.out.println("size is " + heap.getSize() + ", top is " + heap.top());
    heap.insert(36);
    heap.insert(30);
    heap.insert(10);
    while(!heap.isEmpty()) {
      System.out.println(heap.remove());
    }
*/

package datastructure;

import java.util.Arrays;

public class MyHeap {
  private Object[] heap;
  private int capacity;
  private int size;
  public MyHeap() {
    capacity = 8;
    heap = new Object[capacity];
    size = 0;
  }
  private void increaseCapacity() {
    capacity *= 2;
    heap = Arrays.copyOf(heap, capacity);
  }
  public int getSize() {
    return size;
  }
  public boolean isEmpty() {
    return size == 0;
  }
  public Object top() {
    return size > 0 ? heap[0] : null;
  }
  @SuppressWarnings("unchecked")
  public Object remove() {
    if(size == 0) {
      return null;
    }
    size--;
    Object res = heap[0];
    Object te = heap[size];
    int curr = 0, son = 1;
    while(son < size) {
      if(son + 1 < size && ((Comparable<Object>)heap[son + 1]).compareTo(heap[son]) < 0) {
        son++;
      }
      if(((Comparable<Object>)te).compareTo(heap[son]) <= 0) {
        break;
      }
      heap[curr] = heap[son];
      curr = son;
      son = 2 * curr + 1;
    }
    heap[curr] = te;
    return res;
  }
  @SuppressWarnings("unchecked")
  public void insert(Object e) {
    if(size == capacity) { // auto scaling
      increaseCapacity();
    }
    int curr = size;
    int parent;
    heap[size] = e;
    size++;
    while(curr > 0) {
      parent = (curr - 1) / 2;
      if(((Comparable<Object>)heap[parent]).compareTo(e) <= 0) {
        break;
      }
      heap[curr] = heap[parent];
      curr = parent;
    }
    heap[curr] = e;
  }
}
