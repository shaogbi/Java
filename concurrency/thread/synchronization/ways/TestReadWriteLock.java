package concurrency.thread.synchronization.ways;

import java.util.concurrent.locks.*;

// used for ReadWriteLock
class PricesInfo {
  private double price1;
  private double price2;
  private ReadWriteLock lock;
  public PricesInfo() {
    price1 = 1.0;
    price2 = 2.0;
    // if set as true, when multiple threads waiting for a lock, it selects the one who waited longest
    // else it random selects one. default is set as false
    // lock = new ReentrantReadWriteLock(true);
    lock = new ReentrantReadWriteLock();
  }
  public double getPrice1() {
    lock.readLock().lock();
    double value = price1;
    lock.readLock().unlock();
    return value;
  }
  public double getPrice2() {
    lock.readLock().lock();
    double value = price2;
    lock.readLock().unlock();
    return value;
  }
  public void setPrices(double price1, double price2) {
    lock.writeLock().lock();
    this.price1 = price1;
    this.price2 = price2;
    lock.writeLock().unlock();
  }
  /* it also works using "synchronized"
  public synchronized double getPrice1() {
    return price1;
  }
  public synchronized double getPrice2() {
    return price2;
  }
  public synchronized void setPrices(double price1, double price2) {
    this.price1 = price1;
    this.price2 = price2;
  }
  */
}

class Reader implements Runnable {
  private PricesInfo pricesInfo;
  public Reader (PricesInfo pricesInfo){
    this.pricesInfo = pricesInfo;
  }
  @Override
  public void run() {
    for(int i=0;i<10;i++) {
      System.out.printf("%s: Price 1: %f\n", Thread.currentThread().getName(), pricesInfo.getPrice1());
      System.out.printf("%s: Price 2: %f\n", Thread.currentThread().getName(), pricesInfo.getPrice2());
    }
  }
}

class Writer implements Runnable {
  private PricesInfo pricesInfo;
  public Writer(PricesInfo pricesInfo) {
    this.pricesInfo = pricesInfo;
  }
  @Override
  public void run() {
    for (int i=0;i<3;i++) {
      System.out.printf("Writer: Attempt to modify the prices.\n");
      pricesInfo.setPrices(Math.random() * 10, Math.random() * 8);
      System.out.printf("Writer: Prices have been modified.\n");
      try {
        Thread.sleep(2);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

public class TestReadWriteLock {
  public static void main(String[] args) {
    PricesInfo pricesInfo = new PricesInfo();
    int count = 5;
    Reader[] readers = new Reader[count];
    Thread[] threadsReader = new Thread[count];
    for(int i=0;i<count;i++) {
      readers[i] = new Reader(pricesInfo);
      threadsReader[i] = new Thread(readers[i]);
    }
    Writer writer = new Writer(pricesInfo);
    Thread threadWriter = new Thread(writer);
    for(int i=0;i<count;i++) {
      threadsReader[i].start();
    }
    threadWriter.start();
  }
}
