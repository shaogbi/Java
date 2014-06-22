package concurrency.thread.synchronization.tools;

import java.util.*;
import java.util.concurrent.*;

class Producer implements Runnable {
  private List<String> buffer;
  private final Exchanger<List<String>> exchanger;
  public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
    this.buffer = buffer;
    this.exchanger = exchanger;
  }
  @Override
  public void run() {
    int cycle = 1;
    for(int i=0;i<5;i++) {
      System.out.printf("Producer: Cycle %d.\n", cycle);
      for(int j=0;j<10;j++) {
        String resource = "Resource " + ((i * 10) + j);
        System.out.printf("Producer produced: %s.\n", resource);
        buffer.add(resource);
      }
      try {
        buffer = exchanger.exchange(buffer);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
      // "give", not "copy to", so output is 0
      System.out.printf("After exchanged in cycle %d, producer buffer size is %d.\n", cycle, buffer.size());
      cycle++;
    }
  }
}

class Consumer implements Runnable {
  private List<String> buffer;
  private final Exchanger<List<String>> exchanger;
  public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
    this.buffer = buffer;
    this.exchanger = exchanger;
  }
  @Override
  public void run() {
    int cycle = 1;
    for(int i=0;i<5;i++) {
      System.out.printf("Consumer: Cycle %d.\n", cycle);
      try {
        buffer = exchanger.exchange(buffer);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
      for(int j=0;j<10;j++) {
        String resource = buffer.get(0);
        System.out.printf("Consumer get resource: %s.\n", resource);
        buffer.remove(0);
      }
      cycle++;
    }
  }
}

public class TestExchanger {
  public static void main(String[] args) {
    Exchanger<List<String>> exchanger = new Exchanger<List<String>>();
    new Thread(new Producer(new ArrayList<String>(), exchanger)).start();;
    new Thread(new Consumer(new ArrayList<String>(), exchanger)).start();;
  }
}
