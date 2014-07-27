package designpattern.decorator;

public class ConcreteDecorator2 extends Decorator {
  public ConcreteDecorator2(IComponent component) {
    super(component);
  }
  @Override
  public void operate() {
    super.operate();
    System.out.println("I'm coder 2, I can do design, coding and operation.");
  }
}
