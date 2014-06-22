package concurrency.thread.synchronization.ways;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class LockTestHelper {
  private final Lock lock1 = new ReentrantLock();
  private final Lock lock2 = new ReentrantLock();
  //private final Object helper1 = new Object();
  //private final Object helper2 = new Object();
  public void f1() {
    lock1.lock();
    try {
      System.out.println("f1 begins");
      System.out.println(lock1.tryLock()); // true
      TimeUnit.MILLISECONDS.sleep(5000);
      System.out.println("f1 ends");
    } catch(InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock1.unlock();
    }
    /* another way: add lock for helper1
    synchronized(helper1) {
      System.out.println("f1 begins");
      try {
        TimeUnit.MILLISECONDS.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("f1 ends");
    }
    // if use synchronized(this), it means add lock for current instance, f1() and f2() cannot be executed at same time
    */
  }
  public void f2() {
    lock2.lock();
    try {
      System.out.println("f2 begins");
      System.out.println(lock1.tryLock()); // false
      TimeUnit.MILLISECONDS.sleep(5000);
      System.out.println("f2 ends");
    } catch(InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock2.unlock();
    }
    /* another way: add lock for helper2
    synchronized(helper2) {
      System.out.println("f2 begins");
      try {
        TimeUnit.MILLISECONDS.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("f2 ends");
    }
    */
  }
}

class LockTestRunner1 implements Runnable {
  private LockTestHelper lockTestHelper;
  public LockTestRunner1(LockTestHelper lockTestHelper) {
    this.lockTestHelper = lockTestHelper;
  }
  @Override
  public void run() {
    lockTestHelper.f1();
  }
}

class LockTestRunner2 implements Runnable {
  private LockTestHelper lockTestHelper;
  public LockTestRunner2(LockTestHelper lockTestHelper) {
    this.lockTestHelper = lockTestHelper;
  }
  @Override
  public void run() {
    lockTestHelper.f2();
  }
}

public class TestLock {
  public static void main(String[] args) {
    LockTestHelper lockTestHelper = new LockTestHelper();
    // won't be blocked(f1() and f2() starts almost at same time) because each method calls specific lock
    // another way is use synchronized, add lock for different helper object, see code above
    new Thread(new LockTestRunner1(lockTestHelper)).start();
    new Thread(new LockTestRunner2(lockTestHelper)).start();
  }
}
