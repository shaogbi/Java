package concurrency.thread.synchronization.tools;

import java.util.concurrent.*;

class Worker extends Thread {
  private String workerName; 
  private int workTime;
  private CountDownLatch latch;
  public Worker(String workerName, int workTime, CountDownLatch latch) {
     this.workerName = workerName;
     this.workTime = workTime;
     this.latch = latch;
  }
  @Override
  public void run() {
    System.out.printf("Worker %s will do work %d seconds.\n", workerName, workTime);
    try {
      TimeUnit.SECONDS.sleep(workTime);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("Worker %s completed his work.\n", workerName);
    latch.countDown();
  }
}

public class TestCountDownLatch {
  public static void main(String[] args) {
    CountDownLatch latch = new CountDownLatch(2);
    Worker worker1 = new Worker("Tom", 3, latch);
    Worker worker2 = new Worker("Jerry", 5, latch);
    long now = System.currentTimeMillis();
    worker1.start();
    worker2.start();
    try {
      latch.await();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("All work done after %d seconds!\n", (System.currentTimeMillis()-now)/1000);
  }
}

/** output:
 * "CountDownLatch latch = new CountDownLatch(1);" is:
Worker Tom will do work 3 seconds.
Worker Jerry will do work 5 seconds.
Worker Tom completed his work.
All work done after 3 seconds!
Worker Jerry completed his work.
 * "CountDownLatch latch = new CountDownLatch(2);" is:
Worker Tom will do work 3 seconds.
Worker Jerry will do work 5 seconds.
Worker Tom completed his work.
Worker Jerry completed his work.
All work done after 5 seconds!
 * "CountDownLatch latch = new CountDownLatch(3);" is: (it never stops)
Worker Jerry will do work 5 seconds.
Worker Tom will do work 3 seconds.
Worker Tom completed his work.
Worker Jerry completed his work.
*/
