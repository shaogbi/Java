package designpattern.observer;

import java.util.*;

public class MessageCenterSubject implements ISubject {
  private ArrayList<IObserver> observerList;
  public MessageCenterSubject() {
    this.observerList = new ArrayList<IObserver>();
  }
  @Override
  public void addObserver(IObserver observer) {
    observerList.add(observer);
  }
  @Override
  public void removeObserver(IObserver observer) {
    observerList.remove(observer);
  }
  @Override
  public void notifyObservers(String message) {
    for(IObserver observer: observerList) {
      observer.getMessage(message);
    }
  }
  public void doSomething() {
    System.out.println("I will do something.");
    notifyObservers("The object is doing something...");
  }
}
