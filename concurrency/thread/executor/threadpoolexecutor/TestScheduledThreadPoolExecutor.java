package concurrency.thread.executor.threadpoolexecutor;

import java.util.*;
import java.util.concurrent.*;

class ScheduledThreadPoolExecutorTask implements Callable<String> {
  private String name;
  public ScheduledThreadPoolExecutorTask(String name) {
    this.name = name;
  }
  @Override
  public String call() {
    System.out.printf("%s: Starting at : %s.\n", name, new Date());
    return "Hello world!";
  }
}

class RunnableTask implements Runnable {
  private String name;
  public RunnableTask(String name) {
    this.name=name;
  }
  @Override
  public void run() {
    System.out.printf("%s: Starting at : %s\n", name, new Date());
  }
}

public class TestScheduledThreadPoolExecutor {
  public static void main(String[] args) {
    ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1);
    System.out.printf("Main: Starting at: %s.\n", new Date());
    for(int i=0;i<5;i++) {
      ScheduledThreadPoolExecutorTask task = new ScheduledThreadPoolExecutorTask("Task " + i);
      executor.schedule(task, i+1, TimeUnit.SECONDS);
    }
    executor.shutdown();
    try {
      executor.awaitTermination(1, TimeUnit.DAYS);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("Main: Ends at: %s.\n", new Date());
    executor = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1);
    RunnableTask task = new RunnableTask("Task");
    ScheduledFuture<?> result = executor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
    for(int i=0;i<10;i++) {
      System.out.printf("Main: Delay: %d.\n", result.getDelay(TimeUnit.MILLISECONDS));
      try {
        TimeUnit.MILLISECONDS.sleep(500);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
    executor.shutdown();
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("Main: Finished at: %s.\n", new Date());
  }
}
