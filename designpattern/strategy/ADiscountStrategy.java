package designpattern.strategy;

public class ADiscountStrategy implements IDiscountStrategy {
  @Override
  public double getDiscount(double price) {
    return price * 0.9;
  }
}
