package concurrency.thread.executor.threadpoolexecutor;

import java.util.*;
import java.util.concurrent.*;

class Task implements Runnable {
  private Date initDate;
  private String name;
  public Task(String name) {
    this.initDate = new Date();
    this.name = name;
  }
  @Override
  public void run() {
    System.out.printf("%s: Task %s: Created on: %s.\n", Thread.currentThread().getName(), name, initDate);
    System.out.printf("%s: Task %s: Started on: %s.\n", Thread.currentThread().getName(), name, new Date());
    try {
      long duration = (long)(Math.random() * 5);
      System.out.printf("%s: Task %s: Doing a task during %d seconds.\n", Thread.currentThread().getName(), name, duration);
      TimeUnit.SECONDS.sleep(duration);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("%s: Task %s: Finished on: %s.\n", Thread.currentThread().getName(), name, new Date());
  }
}

public class TestThreadPoolExecutor {
  public static void main(String[] args) {
    ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
    //ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
    for(int i=0;i<100;i++) {
      Task task = new Task("Task-" + i);
      System.out.printf("Server: A new task has arrived.\n");
      executor.execute(task);
      System.out.printf("Server: Pool Size: %d.\n", executor.getPoolSize());
      System.out.printf("Server: Active Count: %d.\n", executor.getActiveCount());
      System.out.printf("Server: Completed Tasks: %d.\n", executor.getCompletedTaskCount());
      System.out.printf("Server: Task Count: %d.\n", executor.getTaskCount());
    }
    executor.shutdown();
    System.out.println("execute shut down...");   
  }
}
