package designpattern.observer;

public class StudentObserver implements IObserver {
  private String name;
  public StudentObserver(String name) {
    this.name = name;
  }
  public void getMessage(String message) {
    System.out.printf("%s got a message: %s.\n", name, message);
  }
}
