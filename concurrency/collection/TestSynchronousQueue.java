package concurrency.collection;

import java.util.concurrent.SynchronousQueue;

class QueueProducer implements Runnable {
  private SynchronousQueue<String> queue;
  public QueueProducer(SynchronousQueue<String> queue) {
    this.queue = queue;
  }
  @Override
  public void run() {
    String event1 = "SYNCHRONOUS_EVENT";
    String event2 = "ANOTHER_EVENT";
    try {
      queue.put(event1);
      System.out.printf("[%s] Published event: %s %n", Thread.currentThread().getName(), event1);
      // thread will block here because consumer no more consume
      queue.put(event2);
      System.out.printf("[%s] Published event: %s %n", Thread.currentThread().getName(), event2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class QueueConsumer implements Runnable {
  private SynchronousQueue<String> queue;
  public QueueConsumer(SynchronousQueue<String> queue) {
    this.queue = queue;
  }
  @Override
  public void run() {
    try {
      String event = queue.take();
      System.out.printf("[%s] Consumed event: %s %n", Thread.currentThread().getName(), event);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

public class TestSynchronousQueue {
  public static void main(String[] args) {
    SynchronousQueue<String> queue = new SynchronousQueue<String>();
    new Thread(new QueueConsumer(queue)).start();
    new Thread(new QueueProducer(queue)).start();
  }
}
