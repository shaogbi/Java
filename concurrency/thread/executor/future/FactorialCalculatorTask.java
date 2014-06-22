package concurrency.thread.executor.future;

import java.util.concurrent.*;

public class FactorialCalculatorTask implements Callable<Integer> {
  private Integer number;
  public FactorialCalculatorTask(Integer number) {
    this.number = number;
  }
  public Integer getNumber() {
    return number;
  }
  @Override
  public Integer call() throws Exception {
    int result = 1;
    if(number <= 1) {
      result = 1;
    } else {
      for(int i=2;i<=number;i++) {
        result *= i;
        TimeUnit.MILLISECONDS.sleep(500);
      }
    }
    System.out.printf("%s: %d\n", Thread.currentThread().getName(), result);
    return result;
  }
}
