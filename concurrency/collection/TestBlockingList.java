package concurrency.collection;

import java.util.*;
import java.util.concurrent.*;

class Client implements Runnable {
  private LinkedBlockingDeque<String> requestList;
  public Client(LinkedBlockingDeque<String> requestList) {
    this.requestList = requestList;
  }
  @Override
  public void run() {
    for(int i=0;i<3;i++) {
      for(int j=0;j<5;j++) {
        StringBuilder request = new StringBuilder();
        request.append(i).append("-").append(j);
        try {
          requestList.put(request.toString());
        } catch(InterruptedException e) {
          e.printStackTrace();
        }
        System.out.printf("Client: %s at %s.\n", request, new Date());
      }
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.printf("Client: End.\n");
  }
}

public class TestBlockingList {
  public static void main(String[] args) {
    LinkedBlockingDeque<String> list = new LinkedBlockingDeque<String>();
    Client client = new Client(list);
    Thread thread = new Thread(client);
    thread.start();
    for(int i=0;i<5;i++) {
      for(int j=0;j<3;j++) {
        String request = null;
        try {
          // try request = list.poll(10, TimeUnit.MILLISECONDS); and set list size as 3 to see what will happen
          request = list.take();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.printf("Main: Request: %s at %s. Deque size: %d\n", request, new Date(), list.size());
      }
      try {
        TimeUnit.MILLISECONDS.sleep(300);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
    try {
      thread.join();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("Main: End of the program.\n");
  }
}
