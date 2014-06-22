package concurrency.thread.executor.future;

import java.util.*;
import java.util.concurrent.*;

public class TestFuture {
  public static void main(String[] args) throws Exception {
    ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(3);
    List<Future<Integer>> resultList = new ArrayList<>();
    Random random = new Random();
    for(int i=0;i<10;i++) {
      FactorialCalculatorTask calculator = new FactorialCalculatorTask(random.nextInt(10));
      // after submit, the task runs immediately without blocking! so we can see "Now I can do something else..." displayed quickly
      Future<Integer> result = executor.submit(calculator);
      resultList.add(result);
    }
    executor.shutdown(); // if not shutdown, program will not stop
    /*
    executor.shutdownNow(); // will cause exception when call get()
    */
    System.out.println("Now I can do something else...");
    // this while loop is not necessary, just for demonstration
    while(executor.getCompletedTaskCount() < executor.getTaskCount()) {
      System.out.printf("Main: Number of Completed Tasks: %d.\n", executor.getCompletedTaskCount());
      for(int i=0;i<resultList.size();i++) {
        Future<Integer> result = resultList.get(i);
        System.out.printf("Main: Task %d is done? %s.\n", i, result.isDone());
        if(i == 5) {
          resultList.get(i).cancel(true);
        }
      }
      TimeUnit.MILLISECONDS.sleep(3000);
    }
    // if sleep a long time, result will be output very quickly
    // TimeUnit.MILLISECONDS.sleep(10000);
    System.out.printf("All calculated:\n");
    for(int i=0;i<resultList.size();i++) {
      Integer number = null;
      if(!resultList.get(i).isCancelled()) {
        number = resultList.get(i).get();
      }
      System.out.printf("Main: Task %d's result is %d.\n", i, number);
    }
    System.out.printf("All completed, will exit.\n");
    /* FutureTask can be involved in a thread, it looks like a Runnable
    FutureTask<Integer> futureTask = new FutureTask<Integer>(new FactorialCalculator(10));
    new Thread(futureTask).start();
    */
  }
}
