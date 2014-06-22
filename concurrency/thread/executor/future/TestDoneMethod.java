package concurrency.thread.executor.future;

import java.util.*;
import java.util.concurrent.*;

class ResultTask extends FutureTask<Integer> {
  private Integer number;
  public ResultTask(Callable<Integer> callable) {
    super(callable);
    this.number = ((FactorialCalculatorTask)callable).getNumber();
  }
  @Override
  protected void done() {
    if(isCancelled()) {
      System.out.printf("FactorialCalculator %d: Has been canceled.\n", number);
    } else {
      System.out.printf("FactorialCalculator %d: Has finished.\n", number);
    }
  }
}

public class TestDoneMethod {
  public static void main(String[] args) throws Exception {
    ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
    ResultTask[] resultTasks = new ResultTask[10];
    Random random = new Random();
    for(int i=0;i<10;i++) {
      FactorialCalculatorTask calculator = new FactorialCalculatorTask(random.nextInt(10));
      resultTasks[i] = new ResultTask(calculator);
      executor.submit(resultTasks[i]);
    }
    TimeUnit.MILLISECONDS.sleep(2000);
    for(int i=0;i<resultTasks.length;i++) {
      resultTasks[i].cancel(true);
    }
    System.out.printf("Now begin display results:\n");
    for(int i=0;i<resultTasks.length;i++) {
      if(!resultTasks[i].isCancelled()) {
        System.out.printf("Task %d not cancelled, result is %s\n", i, resultTasks[i].get());
      }
    }
    executor.shutdown();
  }
}
