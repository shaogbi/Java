package concurrency.customized;

import java.util.concurrent.atomic.*;

class ParkingCounter extends AtomicInteger {
  private static final long serialVersionUID = 1L;
  private int maxNumber;
  public ParkingCounter(int maxNumber) {
    set(0);
    this.maxNumber = maxNumber;
  }
  public boolean carIn() {
    while(true) {
      int value = get();
      if(value == maxNumber) {
        System.out.printf("ParkingCounter: The parking lot is full.\n");
        return false;
      } else if(compareAndSet(value, value + 1)) {
        System.out.printf("ParkingCounter: A car has entered.\n");
        return true;
      }
    }
  }
  public boolean carOut() {
    while(true) {
      int value = get();
      if(value == 0) {
        System.out.printf("ParkingCounter: The parking lot is empty.\n");
        return false;
      } else if(compareAndSet(value, value - 1)) {
        System.out.printf("ParkingCounter: A car has gone out.\n");
        return true;
      }
    }
  }
}

class Sensor1 implements Runnable {
  private ParkingCounter counter;
  public Sensor1(ParkingCounter counter) {
    this.counter = counter;
  }
  @Override
  public void run() {
    counter.carIn();
    counter.carIn();
    counter.carIn();
    counter.carIn();
    counter.carOut();
    counter.carOut();
    counter.carOut();
    counter.carIn();
    counter.carIn();
    counter.carIn();
  }
}

class Sensor2 implements Runnable {
  private ParkingCounter counter;
  public Sensor2(ParkingCounter counter) {
    this.counter = counter;
  }
  @Override
  public void run() {
    counter.carIn();
    counter.carOut();
    counter.carOut();
    counter.carIn();
    counter.carIn();
    counter.carIn();
    counter.carIn();
    counter.carIn();
    counter.carIn();
  }
}

public class TestMyAtomicValue {
  public static void main(String[] args) throws Exception {
    ParkingCounter counter = new ParkingCounter(5);
    Sensor1 sensor1 = new Sensor1(counter);
    Sensor2 sensor2 = new Sensor2(counter);
    Thread thread1 = new Thread(sensor1);
    Thread thread2 = new Thread(sensor2);
    thread1.start();
    thread2.start();
    thread1.join();
    thread2.join();
    System.out.printf("Main: Number of cars: %d.\n", counter.get());
    System.out.printf("Main: End of the program.\n");
  }
}
