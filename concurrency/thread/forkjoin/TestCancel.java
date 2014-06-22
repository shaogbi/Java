package concurrency.thread.forkjoin;

import java.util.*;
import java.util.concurrent.*;

class ArrayGenerator {
  public int[] generateArray(int size) {
    int[] array = new int[size];
    Random random = new Random();
    for(int i=0;i<size;i++) {
      array[i] = random.nextInt(10);
    }
    return array;
  }
}

class SearchNumberTask extends RecursiveTask<Integer> {
  private static final long serialVersionUID = 1L;
  private int[] numbers;
  private int start;
  private int end;
  private int target;
  private TaskManager manager;
  private final static int NOT_FOUND = -1;
  public SearchNumberTask(int[] numbers, int start, int end, int target, TaskManager manager) {
    this.numbers = numbers;
    this.start = start;
    this.end = end;
    this.target = target;
    this.manager = manager;
  }
  private int lookForNumber() {
    for(int i=start;i<end;i++) {
      if(numbers[i] == target) {
        System.out.printf("Task: Number %d found in position %d! Will cancel other tasks...\n", target, i);
        manager.cancelTasks(this);
        return i;
      }
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
    return NOT_FOUND;
  }
  private int launchTasks() {
    int mid = (start + end) / 2;
    SearchNumberTask task1 = new SearchNumberTask(numbers, start, mid, target, manager);
    SearchNumberTask task2 = new SearchNumberTask(numbers, mid, end, target, manager);
    manager.addTask(task1);
    manager.addTask(task2);
    task1.fork();
    task2.fork();
    int returnValue = task1.join();
    if(returnValue != NOT_FOUND) {
      return returnValue;
    }
    returnValue = task2.join();
    return returnValue;
  }
  public void writeCancelMessage() {
    System.out.printf("Task: Canceled task from %d to %d.\n", start, end);
  }
  @Override
  protected Integer compute() {
    System.out.printf("Task: from %d to %d.\n", start, end);
    int ret;
    if(end - start > 10) {
      ret = launchTasks();
    } else {
      ret = lookForNumber();
    }
    return ret;
  }
}

class TaskManager {
  private List<ForkJoinTask<Integer>> tasks;
  public TaskManager() {
    tasks = new ArrayList<>();
  }
  public void addTask(ForkJoinTask<Integer> task) {
    tasks.add(task);
  }
  public void cancelTasks(ForkJoinTask<Integer> cancelTask) {
    for(ForkJoinTask<Integer> task :tasks) {
      if(task != cancelTask) {
        task.cancel(true);
        ((SearchNumberTask)task).writeCancelMessage();
      }
    }
  }
}

public class TestCancel {
  public static void main(String[] args) throws Exception {
    int[] numbers = new ArrayGenerator().generateArray(1000);
    TaskManager manager = new TaskManager();
    ForkJoinPool pool = new ForkJoinPool();
    SearchNumberTask task = new SearchNumberTask(numbers, 0, 1000, 5, manager);
    pool.execute(task);
    pool.shutdown();
    while(!pool.isTerminated()) {
      pool.awaitTermination(1, TimeUnit.SECONDS);
    }
    System.out.printf("Main: The program has finished.\n");
  }
}
