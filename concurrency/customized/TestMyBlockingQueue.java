package concurrency.customized;

import java.util.*;

class MyBlockingQueue {
  private List<Object> queue = new LinkedList<Object>();
  private int limit = 3;
  public MyBlockingQueue() {}
  public MyBlockingQueue(int limit) {
    this.limit = limit;
  }
  public synchronized void enqueue(Object item) throws InterruptedException {
    if(this.queue.size() == 0) {
      notifyAll();
    }
    while(this.queue.size() == this.limit) {
      wait();
    }
    
    this.queue.add(item);
    System.out.println(item + " added");
  }
  public synchronized Object dequeue() throws InterruptedException {
    if(this.queue.size() == this.limit) {
      notifyAll();
      System.out.println("notify you");
    }
    while(this.queue.size() == 0) {
      wait();
    }
    
    return this.queue.remove(0);
  }
}

public class TestMyBlockingQueue {
  public static void main(String[] args) throws InterruptedException {
    MyBlockingQueue myBlockingQueue = new MyBlockingQueue();
    myBlockingQueue.enqueue(new Object());
    myBlockingQueue.enqueue(new Object());
    myBlockingQueue.enqueue(new Object());
    System.out.println(myBlockingQueue.dequeue());
    System.out.println(myBlockingQueue.dequeue());
    System.out.println(myBlockingQueue.dequeue());
    System.out.println(myBlockingQueue.dequeue());
    myBlockingQueue.enqueue(new Object());
  }
}
