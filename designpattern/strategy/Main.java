package designpattern.strategy;

public class Main {
  public static void main(String[] args) {
    DiscountContext discountContext = new DiscountContext(new ADiscountStrategy());
    double price = 100.0;
    System.out.println("Strategy A: " + discountContext.getDiscount(price));
    discountContext.setDiscountStrategy(new BDiscountStrategy());
    System.out.println("Strategy B: " + discountContext.getDiscount(price));
  }
}
