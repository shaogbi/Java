package designpattern.decorator;

public class ConcreteComponent implements IComponent {
  @Override
  public void operate() {
		System.out.println("New project is coming...");
	}
}
