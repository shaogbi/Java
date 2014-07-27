package designpattern.decorator;

public class Main {
  public static void main(String[] args) {
    IComponent project = new ConcreteComponent();
    Decorator coder1 = new ConcreteDecorator(project);
    System.out.println("Coder 1:");
    coder1.operate();
    System.out.println("Coder 2:");
    Decorator coder2 = new ConcreteDecorator2(project);
    coder2.operate();
  }
}
