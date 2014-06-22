package concurrency.collection;

import java.util.*;
import java.util.concurrent.*;

class DelayedEvent implements Delayed {
  private final long triggerTime;
  // "delayInMilliseconds" means how many milliseconds delayed(after "delayInMilliseconds" milliseconds, element will be available)
  public DelayedEvent(long delayInMilliseconds) {
    this.triggerTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delayInMilliseconds, TimeUnit.MILLISECONDS);
  }
  // when getDelay returns negative value, element is available
  @Override
  public long getDelay(TimeUnit unit) {
    return unit.convert(triggerTime - System.nanoTime(), TimeUnit.NANOSECONDS);
  }
  @Override
  public int compareTo(Delayed e) {
    long offset = triggerTime - ((DelayedEvent)e).triggerTime;
    if(offset > 0) {
      return 1;
    } else if(offset < 0) {
      return -1;
    } else {
      return 0;
    }
  }
}

class DelayedTask implements Runnable {
  private int id;
  private DelayQueue<DelayedEvent> queue;
  public DelayedTask(int id, DelayQueue<DelayedEvent> queue) {
    this.id = id;
    this.queue = queue;
  }
  @Override
  public void run() {
    long delayInMilliseconds = id * 1000;
    System.out.printf("Thread %d delays %d milliseconds.\n", id, delayInMilliseconds);
    for(int i=0;i<100;i++) {
      DelayedEvent event = new DelayedEvent(delayInMilliseconds);
      queue.add(event);
    }
  }
}

public class TestDelayedElement {
  public static void main(String[] args) throws Exception {
    DelayQueue<DelayedEvent> queue = new DelayQueue<DelayedEvent>();
    Thread[] threads = new Thread[5];
    for(int i=0;i<5;i++) {
      DelayedTask task = new DelayedTask(i+1, queue);
      threads[i] = new Thread(task);
    }
    for(int i=0;i<5;i++) {
      threads[i].start();
    }
    for(int i=0;i<5;i++) {
      threads[i].join();
    }
    do {
      int counter = 0;
      DelayedEvent event = null;
      do {
        event = queue.poll();
        if(event != null) {
          System.out.println(event.getDelay(TimeUnit.MILLISECONDS));
          counter++;
        }
      } while(event != null);
      System.out.printf("At %s you have read %d events.\n", new Date(), counter);
      TimeUnit.MILLISECONDS.sleep(500);
    } while(queue.size() > 0);
  }
}
