package concurrency.thread.forkjoin;

import java.util.concurrent.*;

public class ExecutionTraceTool {
  public static void outputCurrentExecutionState(ForkJoinPool pool, String unit, long sleepUnits) {
    System.out.printf("******************************************\n");
    System.out.printf("Main: Parallelism: %d.\n", pool.getParallelism());
    System.out.printf("Main: Active Threads: %d.\n", pool.getActiveThreadCount());
    System.out.printf("Main: Task Count: %d.\n", pool.getQueuedTaskCount());
    System.out.printf("Main: Steal Count: %d.\n",pool.getStealCount());
    System.out.printf("******************************************\n");
    try {
      switch(unit) {
      case "SECOND":
        TimeUnit.SECONDS.sleep(sleepUnits);
        break;
      case "MILLISECOND":
        TimeUnit.MILLISECONDS.sleep(sleepUnits);
        break;
      }
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }
}
