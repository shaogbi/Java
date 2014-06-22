package concurrency.customized;

import java.util.concurrent.*;

class MyPriorityTask implements Runnable, Comparable<MyPriorityTask> {
  private int priority;
  private String name;
  public MyPriorityTask(String name, int priority) {
    this.name = name;
    this.priority = priority;
  }
  public int getPriority() {
    return priority;
  }
  @Override
  public int compareTo(MyPriorityTask o) {
    if(this.getPriority() < o.getPriority()) {
      return 1;
    } else if(this.getPriority() > o.getPriority()) {
      return -1;
    }
    return 0;
  }
  @Override
  public void run() {
    System.out.printf("MyPriorityTask: %s Priority : %d.\n", name, priority);
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }
}

public class TestPriorityTask {
  public static void main(String[] args) throws Exception {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
    for(int i=0;i<4;i++) {
      MyPriorityTask task = new MyPriorityTask("Task "+i, i);
      executor.execute(task);
    }
    TimeUnit.SECONDS.sleep(1);
    for(int i=4;i<8;i++) {
      MyPriorityTask task = new MyPriorityTask("Task "+i, i);
      executor.execute(task);
    }
    executor.shutdown();
    executor.awaitTermination(1, TimeUnit.DAYS);
    /* or
    while(executor.getCompletedTaskCount() < executor.getTaskCount()) { // or while(!executor.isTerminated())
      TimeUnit.SECONDS.sleep(1);
    }
    */
    System.out.printf("Main: End of the program.\n");
  }
}
