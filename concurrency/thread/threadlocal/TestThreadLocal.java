package concurrency.thread.threadlocal;

import java.util.concurrent.*;

class SafeTask implements Runnable {
  private ThreadLocal<Integer> num = new ThreadLocal<Integer>() {
    @Override
    protected Integer initialValue() {
      return 3;
    }
  };
  @Override
  public void run() {
    // although ThreadLocal can be regarded as duplication for different threads, num.hashCode() is always same
    System.out.printf("Safe Thread %s: %d\n", Thread.currentThread().getId(), num.get());
    num.set(num.get() + 1);
    System.out.printf("Safe Thread %s: %d\n", Thread.currentThread().getId(), num.get());
  }
}

class UnsafeTask implements Runnable {
  private Integer num = 3;
  @Override
  public void run() {
    num++;
    System.out.printf("Unsafe Thread %s: %d\n", Thread.currentThread().getId(), num); // 每次调用都会加1
  }
}

public class TestThreadLocal {
  public static void main(String[] args) {
    SafeTask safeTask = new SafeTask();
    UnsafeTask unsafeTask = new UnsafeTask();
    ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
    for(int i=0;i<5;i++) {
      executor.execute(safeTask); // each thread outputs 3 and 4, means each thread's "num" is separate
      executor.execute(unsafeTask); // each thread outputs randomly, means all threads share one "num"
    }
    executor.shutdown();
  }
}
