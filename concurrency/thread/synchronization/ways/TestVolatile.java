package concurrency.thread.synchronization.ways;

class StopTester implements Runnable {
  //private volatile boolean stop = false;
  private boolean stop = false;
  public void stopMe() {
    stop = true;
  }
  @Override
  public void run() {
    while(!stop) {}
    System.out.println("Thread stopped.");
  }
}

public class TestVolatile {
  public static void main(String[] args) throws Exception {
    StopTester tester = new StopTester();
    Thread thread = new Thread(tester);
    thread.start();
    Thread.sleep(1000);
    System.out.println("Try stop...");
    tester.stopMe();
    Thread.sleep(1000);
  }
}
