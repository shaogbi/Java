package concurrency.thread.executor.future;

import java.util.concurrent.*;

public class TestExecutorCompletionService {
  public static void main(String[] args) {
    ExecutorService executor = (ExecutorService)Executors.newCachedThreadPool();
    CompletionService<Integer> service = new ExecutorCompletionService<>(executor);
    for(int i=0;i<5;i++) {
      service.submit(new FactorialCalculatorTask(5 + i));
    }
    executor.shutdown();
    System.out.println("All tasks submitted.");
    try {
      for(int i=0;i<5;i++) {
        Future<Integer> future = service.take();
        System.out.printf("Get result: %s.\n", future.get());
      }
    } catch(InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}
