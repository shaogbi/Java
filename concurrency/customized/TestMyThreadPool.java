package concurrency.customized;

import java.util.*;

class PoolThread extends Thread {
  private MyBlockingQueue taskQueue;
  private boolean isStopped = false;
  public PoolThread(MyBlockingQueue queue) {
    taskQueue = queue;
  }
  public synchronized boolean isStopped() {
    return isStopped;
  }
  public synchronized void toStop() {
    isStopped = true;
    this.interrupt();
  }
  @Override
  public void run() {
    while(!isStopped()) {
      try {
        Runnable runnable = (Runnable)taskQueue.dequeue();
        runnable.run();
      } catch(Exception e) {}
    }
  }
}

class MyThreadPool {
  private MyBlockingQueue taskQueue = null;
  private List<PoolThread> threads = new ArrayList<PoolThread>();
  private boolean isStopped = false;
  public MyThreadPool(int noOfThreads, int maxNoOfTasks) {
    taskQueue = new MyBlockingQueue(maxNoOfTasks);
    for(int i=0;i<noOfThreads;i++) {
      threads.add(new PoolThread(taskQueue));
    }
    for(PoolThread thread : threads) {
      thread.start();
    }
  }
  public synchronized void execute(Runnable task) {
    if(this.isStopped) {
      throw new IllegalStateException("ThreadPool is stopped!");
    }
    try {
      this.taskQueue.enqueue(task);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  public synchronized void stop() {
    this.isStopped = true;
    for(PoolThread thread : threads) {
      thread.toStop();
    }
  }
}

public class TestMyThreadPool {
  @SuppressWarnings("unused")
  public static void main(String[] args) {
    MyThreadPool myThreadPool = new MyThreadPool(3, 5);
  }
}
