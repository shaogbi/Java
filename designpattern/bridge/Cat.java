package designpattern.bridge;

public class Cat implements IAnimal {
  private IFood food;
  @Override
  public void setFood(IFood food) {
    this.food = food;
  }
  @Override
  public void displayFood() {
    System.out.printf("Cat's food is %s.\n", food.getFoodName());
  }
}
