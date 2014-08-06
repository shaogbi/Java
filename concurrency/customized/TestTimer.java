package concurrency.customized;

import java.util.*;
import java.util.concurrent.*;
 
class MyTimerTask extends TimerTask {
  private void runTask() {
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void run() {
    System.out.printf("Timer task started at: %s.\n", new Date());
    runTask();
    System.out.printf("Timer task finished at: %s.\n", new Date());
  }
}

public class TestTimer {
  public static void main(String[] args) throws InterruptedException {
    TimerTask timerTask = new MyTimerTask();
    // running timer task as daemon thread
    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(timerTask, 0, 4000);
    System.out.println("TimerTask started.");
    TimeUnit.SECONDS.sleep(10);
    timer.cancel(); // it immediately stops
    System.out.println("TimerTask cancelled.");
  }
}

/*
suppose TimerTask's running time is t seconds,
if period less than t, it starts every t seconds,
else it starts every period seconds.
when "timer.scheduleAtFixedRate(timerTask, 0, 2000);"
output:
TimerTask started.
Timer task started at: Mon May 12 23:15:11 CST 2014.
Timer task finished at: Mon May 12 23:15:14 CST 2014.
Timer task started at: Mon May 12 23:15:14 CST 2014.
Timer task finished at: Mon May 12 23:15:17 CST 2014.
Timer task started at: Mon May 12 23:15:17 CST 2014.
Timer task finished at: Mon May 12 23:15:20 CST 2014.
Timer task started at: Mon May 12 23:15:20 CST 2014.
TimerTask cancelled.
when "timer.scheduleAtFixedRate(timerTask, 0, 4000);"
output:
TimerTask started.
Timer task started at: Mon May 12 23:16:01 CST 2014.
Timer task finished at: Mon May 12 23:16:04 CST 2014.
Timer task started at: Mon May 12 23:16:05 CST 2014.
Timer task finished at: Mon May 12 23:16:08 CST 2014.
Timer task started at: Mon May 12 23:16:09 CST 2014.
TimerTask cancelled.
*/
