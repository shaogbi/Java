package concurrency.collection;

import java.util.concurrent.*;

class AddTask implements Runnable {
  private ConcurrentLinkedDeque<String> list;
  private CountDownLatch latch;
  public AddTask(ConcurrentLinkedDeque<String> list, CountDownLatch latch) {
    this.list = list;
    this.latch = latch;
  }
  @Override
  public void run() {
    String name = Thread.currentThread().getName();
    for(int i=0;i<10000;i++) {
      list.add(name + ": Element " + i);
    }
    latch.countDown();
  }
}

class PollTask implements Runnable {
  private ConcurrentLinkedDeque<String> list;
  private CountDownLatch latch;
  public PollTask(ConcurrentLinkedDeque<String> list, CountDownLatch latch) {
    this.list = list;
    this.latch = latch;
  }
  @Override
  public void run() {
    for(int i=0;i<5001;i++) {
      list.pollFirst(); // if list already empty, use list.removeFirst() will get Exception
      list.pollLast();
    }
    latch.countDown();
  }
}

public class TesNonBlockingList {
  public static void main(String[] args) throws Exception {
    final int THREAD_COUNT = 100;
    ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<String>();
    Thread[] threads = new Thread[THREAD_COUNT];
    CountDownLatch addTaskLatch = new CountDownLatch(THREAD_COUNT);
    CountDownLatch pollTaskLatch = new CountDownLatch(THREAD_COUNT);
    for(int i=0;i<threads.length;i++) {
      AddTask task = new AddTask(list, addTaskLatch);
      threads[i] = new Thread(task);
      threads[i].start();
    }
    System.out.printf("Main: %d AddTask threads have been launched.\n", threads.length);
    addTaskLatch.await();
    System.out.printf("Main: Size of the List: %d.\n", list.size());
    for(int i=0;i<threads.length;i++) {
      PollTask task = new PollTask(list, pollTaskLatch);
      threads[i] = new Thread(task);
      threads[i].start();
    }
    System.out.printf("Main: %d PollTask threads have been launched.\n", threads.length);
    pollTaskLatch.await();
    System.out.printf("Main: Size of the List: %d.\n", list.size());
  }
}
