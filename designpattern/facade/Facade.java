package designpattern.facade;

public class Facade {
  private Payment payment;
  private Cook cook;
  private Waiter waiter;
  public Facade() {
    payment = new Payment();
    cook = new Cook();
    waiter = new Waiter();
  }
  public void serveFood(String food, double price) {
    cook.cooking(food);
    waiter.serve(food);
    payment.pay(price);
  }
}
