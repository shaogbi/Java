package concurrency.collection;

import java.util.*;
import java.util.concurrent.*;

class Contact {
  private String name;
  private String phone;
  public Contact(String name, String phone) {
    this.name = name;
    this.phone = phone;
  }
  public String getName() {
    return name;
  }
  public String getPhone() {
    return phone;
  }
}

class NavigableTask implements Runnable {
  private String id;
  private ConcurrentSkipListMap<String, Contact> map;
  public NavigableTask(String id, ConcurrentSkipListMap<String, Contact> map) {
    this.id = id;
    this.map = map;
  }
  @Override
  public void run() {
    for(int i=0;i<1000;i++) {
      Contact contact = new Contact(id, String.valueOf(i+1000));
      map.put(id + contact.getPhone(), contact);
    }
  }
}

public class TestNavigableMap {
  public static void main(String[] args) throws Exception {
    ConcurrentSkipListMap<String, Contact> map = new ConcurrentSkipListMap<>();
    Thread[] threads = new Thread[26];
    int counter = 0;
    for(char i='A';i<='Z';i++) {
      NavigableTask task = new NavigableTask(String.valueOf(i), map);
      threads[counter] = new Thread(task);
      threads[counter].start();
      counter++;
    }
    for(int i=0;i<26;i++) {
      threads[i].join();
    }
    System.out.printf("Main: Size of the map: %d.\n", map.size());
    Map.Entry<String, Contact> element;
    Contact contact;
    element = map.firstEntry();
    contact = element.getValue();
    System.out.printf("Main: First Entry: %s-%s.\n", contact.getName(), contact.getPhone());
    element = map.lastEntry();
    contact = element.getValue();
    System.out.printf("Main: Last Entry: %s-%s.\n", contact.getName(), contact.getPhone());
    System.out.printf("Main: Submap from A1996 to B1002: \n");
    ConcurrentNavigableMap<String, Contact> submap = map.subMap("A1996", "B1002");
    do {
      element = submap.pollFirstEntry();
      if(element != null) {
        contact = element.getValue();
        System.out.printf("%s-%s.\n", contact.getName(), contact.getPhone());
      }
    } while(element != null);
    System.out.println("Test ConcurrentHashMap:");
    ConcurrentHashMap<String, Contact> hashMap = new ConcurrentHashMap<>();
    for(char i='A';i<='C';i++) {
      for(int j=1000;j<1010;j++) {
        contact = new Contact(String.valueOf(i), String.valueOf(j));
        hashMap.put(i + contact.getPhone(), contact);
      }
    }
    for(Map.Entry<String, Contact> e: hashMap.entrySet()) {
      System.out.printf("%s: %s-%s.\n", e.getKey(), e.getValue().getName(), e.getValue().getPhone());
    }
  }
}
