package designpattern.bridge;

public class Dog implements IAnimal {
  private IFood food;
  @Override
  public void setFood(IFood food) {
    this.food = food;
  }
  @Override
  public void displayFood() {
    System.out.printf("Dog's food is %s.\n", food.getFoodName());
  }
}
