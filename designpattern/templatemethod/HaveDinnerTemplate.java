package designpattern.templatemethod;

public class HaveDinnerTemplate extends Template {
  @Override
  public void firstStep() {
    System.out.println("order dishes");
  }
  @Override
  public void secondStep() {
    System.out.println("have dishes");
  }
  @Override
  public void thirdStep() {
    System.out.println("bill");
  }
}
