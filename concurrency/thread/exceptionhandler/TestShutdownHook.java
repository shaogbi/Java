package concurrency.thread.exceptionhandler;

class MyShutdownHook extends Thread {
  @Override
  public void run() {
    System.out.println("Will shutdown...");
  }
}

public class TestShutdownHook {
  public static void main(String[] args) {
    Runtime.getRuntime().addShutdownHook(new MyShutdownHook());
    try {
      System.out.println("Hello!");
      int temp = 1 / 0;
      System.out.println(temp);
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Finally.");
    }
    int temp2 = 1 / 0;
    System.out.println(temp2);
  }
}
