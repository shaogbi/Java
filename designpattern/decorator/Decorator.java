package designpattern.decorator;

public class Decorator implements IComponent {
  private IComponent component;
  public Decorator(IComponent component) {
    this.component = component;
  }
  @Override
  public void operate() {
    component.operate();
  }
}
