package designpattern.strategy;

public class BDiscountStrategy implements IDiscountStrategy {
  @Override
  public double getDiscount(double price) {
    return price * 0.8;
  }
}
