package concurrency.thread.executor.threadpoolexecutor;

import java.util.*;
import java.util.concurrent.*;

class Result {
  private String name;
  private int value;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getValue() {
    return value;
  }
  public void setValue(int value) {
    this.value = value;
  }
}

class TestInvokeAllTask implements Callable<Result> {
  private String name;
  public TestInvokeAllTask(String name) {
    this.name = name;
  }
  @Override
  public Result call() {
    System.out.printf("%s: Staring.\n", name);
    try {
      long duration = (long)(Math.random() * 10);
      System.out.printf("%s: Waiting %d seconds for results.\n", name, duration);
      TimeUnit.SECONDS.sleep(duration);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    int value = 0;
    for(int i=0;i<5;i++) {
      value += (int)(Math.random()*100);
    }
    Result result = new Result();
    result.setName(this.name);
    result.setValue(value);
    System.out.printf("%s: Ends.\n", name);
    return result;
  }
}

public class TestInvokeAllMethod {
  public static void main(String[] args) {
    ExecutorService executor = (ExecutorService)Executors.newCachedThreadPool();
    List<TestInvokeAllTask> taskList = new ArrayList<>();
    for(int i=0;i<3;i++) {
      TestInvokeAllTask task = new TestInvokeAllTask(i + "");
      taskList.add(task);
    }
    List<Future<Result>> resultList = null;
    try {
      resultList = executor.invokeAll(taskList);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    executor.shutdown();
    System.out.println("Main: Printing the results");
    for(int i=0;i<resultList.size();i++) {
      Future<Result> future = resultList.get(i);
      try {
        Result result = future.get();
        System.out.printf("Result %s: %s\n", result.getName(), result.getValue());
      } catch(InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }
}
