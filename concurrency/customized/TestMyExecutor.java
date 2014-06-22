package concurrency.customized;

import java.util.*;
import java.util.concurrent.*;

class SleepTwoSecondsTask implements Callable<String> {
  @Override
  public String call() throws Exception {
    TimeUnit.SECONDS.sleep(2);
    return new Date().toString();
  }
}

class MyExecutor extends ThreadPoolExecutor {
  private ConcurrentHashMap<String, Date> startTimes;
  public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    this.startTimes = new ConcurrentHashMap<>();
  }
  private void outputExecutorState() {
    System.out.printf("MyExecutor: Executed tasks: %d.\n", getCompletedTaskCount());
    System.out.printf("MyExecutor: Running tasks: %d.\n", getActiveCount());
    System.out.printf("MyExecutor: Pending tasks: %d.\n", getQueue().size());
  }
  @Override
  public void shutdown() {
    System.out.printf("MyExecutor: Going to shutdown.\n");
    outputExecutorState();
    super.shutdown();
  }
  @Override
  public List<Runnable> shutdownNow() {
    System.out.printf("MyExecutor: Going to immediately shutdown.\n");
    outputExecutorState();
    return super.shutdownNow();
  }
  @Override
  protected void beforeExecute(Thread t, Runnable r) {
    System.out.printf("MyExecutor: A task is beginning: %s : %s.\n", t.getName(), r.hashCode());
    startTimes.put(String.valueOf(r.hashCode()), new Date());
  }
  @Override
  protected void afterExecute(Runnable r, Throwable t) {
    Future<?> result = (Future<?>)r;
    try {
      System.out.printf("*********************************\n");
      System.out.printf("MyExecutor: Task %s is finishing.\n", r.hashCode());
      System.out.printf("MyExecutor: %s Result: %s.\n", r.hashCode(), result.get());
      Date startDate = startTimes.remove(String.valueOf(r.hashCode()));
      Date finishDate = new Date();
      long diff = finishDate.getTime() - startDate.getTime();
      System.out.printf("MyExecutor: %s Duration: %d.\n", r.hashCode(), diff);
      System.out.printf("*********************************\n");
    } catch(InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}

public class TestMyExecutor {
  public static void main(String[] args) throws Exception {
    MyExecutor myExecutor = new MyExecutor(2, 4, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
    List<Future<String>> results = new ArrayList<>();
    for(int i=0;i<10;i++) {
      SleepTwoSecondsTask task = new SleepTwoSecondsTask();
      Future<String> result = myExecutor.submit(task);
      results.add(result);
    }
    for(int i=0;i<5;i++) {
      String result = results.get(i).get();
      System.out.printf("Main: Result for Task %d : %s.\n", i, result);
    }
    myExecutor.shutdown(); // if myExecutor.shutdownNow(); following will get Exception
    for(int i=5;i<10;i++) {
      String result = results.get(i).get();
      System.out.printf("Main: Result for Task %d : %s.\n", i, result);
    }
    myExecutor.awaitTermination(1, TimeUnit.DAYS);
    System.out.printf("Main: End of the program.\n");
  }
}
