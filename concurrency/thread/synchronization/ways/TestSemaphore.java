package concurrency.thread.synchronization.ways;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class PrintQueueForSemaphore {
  private final int COUNT = 3;
  private final Semaphore semaphore;
  private boolean[] freePrinters;
  private Lock lockPrinters;
  public PrintQueueForSemaphore() {
    semaphore = new Semaphore(COUNT);
    lockPrinters = new ReentrantLock();
    freePrinters = new boolean[COUNT];
    for(int i=0;i<COUNT;i++) {
      freePrinters[i] = true;
    }
  }
  private int getPrinter() {
    int ret = -1;
    try {
      lockPrinters.lock();
      for(int i=0;i<freePrinters.length;i++) {
        if(freePrinters[i]) {
          ret = i;
          freePrinters[i] = false;
          break;
        }
      }
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      lockPrinters.unlock();
    }
    return ret;
  }
  public void printJob(Object document) {
    try {
      semaphore.acquire();
      int assignedPrinter = getPrinter();
      long duration = (long)(Math.random()*10);
      System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(), duration);
      System.out.printf("%d enter permissions available.\n", COUNT - semaphore.availablePermits());
      TimeUnit.SECONDS.sleep(duration);
      freePrinters[assignedPrinter] = true;
    } catch(InterruptedException e) {
      e.printStackTrace();
    } finally {
      semaphore.release();
    }
  }
}

class JobForSemaphore implements Runnable {
  private PrintQueueForSemaphore printQueue;
  public JobForSemaphore(PrintQueueForSemaphore printQueue) {
    this.printQueue = printQueue;
  }
  @Override
  public void run() { 
    System.out.printf("%s: Going to print a job\n", Thread.currentThread().getName());
    printQueue.printJob(new Object());
    System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
  }
}

public class TestSemaphore {
  public static void main(String[] args) {
    PrintQueueForSemaphore printQueue = new PrintQueueForSemaphore();
    for(int i=0;i<10;i++) {
      new Thread(new JobForSemaphore(printQueue), "Thread " + i).start();
    }
  }
}
