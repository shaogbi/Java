package concurrency.collection;

import java.util.concurrent.*;

class TaskLocalRandom implements Runnable {
  @Override
  public void run() {
    String name = Thread.currentThread().getName();
    for(int i=0;i<10;i++) {
      System.out.printf("%s: %d.\n", name, ThreadLocalRandom.current().nextInt(10));
    }
  }
}

public class TestRandomGenerator {
  public static void main(String[] args) {
    Thread[] threads = new Thread[3];
    for(int i=0;i<3;i++) {
      TaskLocalRandom task = new TaskLocalRandom();
      threads[i] = new Thread(task);
      threads[i].start();
    }
  }
}
