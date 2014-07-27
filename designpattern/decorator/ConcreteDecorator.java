package designpattern.decorator;

public class ConcreteDecorator extends Decorator {
  public ConcreteDecorator(IComponent component) {
    super(component);
  }
  @Override
  public void operate() {
    super.operate();
    System.out.println("I'm coder 1, I can do design and coding.");
  }
}
