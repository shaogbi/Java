package designpattern.bridge;

public class Main {
  public static void main(String[] args) {
    IFood fish = new Fish();
    IFood meat = new Meat();
    IAnimal cat = new Cat();
    cat.setFood(fish);
    cat.displayFood();
    cat.setFood(meat);
    cat.displayFood();
    IAnimal dog = new Dog();
    dog.setFood(meat);
    dog.displayFood();
  }
}
