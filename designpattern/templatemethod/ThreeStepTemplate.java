package designpattern.templatemethod;

public class ThreeStepTemplate extends Template {
  @Override
  public void firstStep() {
    System.out.println("step 1");
  }
  @Override
  public void secondStep() {
    System.out.println("step 2");
  }
  @Override
  public void thirdStep() {
    System.out.println("step 3");
  }
}
