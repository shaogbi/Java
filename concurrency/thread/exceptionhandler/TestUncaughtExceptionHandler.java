package concurrency.thread.exceptionhandler;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.TimeUnit;

class UnsafeTask implements Runnable {
  @Override
  public void run() {
    System.out.println(Integer.parseInt("123"));
    System.out.println(Integer.parseInt("234"));
    System.out.println(Integer.parseInt("345"));
    System.out.println(Integer.parseInt("XYZ"));
    System.out.println(Integer.parseInt("456"));
  }
}

class SafeTask implements Runnable {
  @Override
  public void run() {
    Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler());
    System.out.println(Integer.parseInt("123"));
    System.out.println(Integer.parseInt("234"));
    System.out.println(Integer.parseInt("345"));
    System.out.println(Integer.parseInt("XYZ"));
    System.out.println(Integer.parseInt("456"));
  }
}

class ExceptionHandler implements UncaughtExceptionHandler {
  @Override
  public void uncaughtException(Thread t, Throwable e) {
    System.out.printf("An exception has been captured\n");
    System.out.printf("Thread: %s\n", t.getId());
    System.out.printf("Exception: %s: %s\n", e.getClass().getName(), e.getMessage());
    System.out.printf("Stack Trace:\n");
    e.printStackTrace();
    System.out.printf("Thread status: %s\n", t.getState());
  }
}

public class TestUncaughtExceptionHandler {
  public static void main(String[] args) {
    UnsafeTask unsafeTask = new UnsafeTask();
    Thread thread = new Thread(unsafeTask);
    thread.start();
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    SafeTask safeTask = new SafeTask();
    Thread thread2 = new Thread(safeTask);
    thread2.start();
  }
}
