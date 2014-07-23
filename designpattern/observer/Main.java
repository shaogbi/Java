package designpattern.observer;

public class Main {
  public static void main(String[] args) {
    MessageCenterSubject messageCenterSubject = new MessageCenterSubject();
    for(int i=0;i<5;i++) {
      messageCenterSubject.addObserver(new StudentObserver("Student " + i));
    }
    messageCenterSubject.notifyObservers("this is a test message");
    messageCenterSubject.doSomething();
  }
}
