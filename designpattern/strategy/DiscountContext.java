package designpattern.strategy;

public class DiscountContext {
  private IDiscountStrategy discountStrategy;
  public DiscountContext(IDiscountStrategy discountStrategy) {
    this.discountStrategy = discountStrategy;
  }
  public void setDiscountStrategy(IDiscountStrategy discountStrategy) {
    this.discountStrategy = discountStrategy;
  }
  public double getDiscount(double price) {
    return discountStrategy.getDiscount(price);
  }
}
