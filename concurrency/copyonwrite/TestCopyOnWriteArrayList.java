package concurrency.copyonwrite;

import java.util.*;
import java.util.concurrent.*;

public class TestCopyOnWriteArrayList {
  public static void main(String[] args) {
    final CopyOnWriteArrayList<Integer> arr = new CopyOnWriteArrayList<Integer>();
    for(int i=0;i<10;i++) {
      arr.add(i);
    }
    /* will cause infinite loop because iterator uses original arraylist's snapshot
    new Thread(new Runnable() {
      @Override
      public void run() {
        Iterator<Integer> iterator = arr.iterator();
        while(iterator.hasNext()) {
          try {
            TimeUnit.MILLISECONDS.sleep(3);
          } catch(InterruptedException e) {}
          arr.clear();
          System.out.println(iterator.next());
        }
      }
    }).start();
    */
    new Thread(new Runnable() {
      @Override
      public void run() {
        Iterator<Integer> iterator = arr.iterator();
        while(iterator.hasNext()) {
          try {
            TimeUnit.MILLISECONDS.sleep(100);
          } catch(InterruptedException e) {}
          System.out.println(iterator.next());
        }
      }
    }).start();
    try {
      TimeUnit.MILLISECONDS.sleep(100);
    } catch(InterruptedException e) {}
    arr.clear(); // although cleared, above thread still output 0 to 9 because iterator uses original arraylist's snapshot
  }
}
