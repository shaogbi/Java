package designpattern.proxy;

public class Printer implements IPrinter {
  public Printer() {
    try {
      System.out.println("Printer initialing...");
      Thread.sleep(3000);
    } catch(Exception e) {}
  }
  @Override
  public void printMessage(String message) {
    System.out.println("Message: " + message);
  }
}
