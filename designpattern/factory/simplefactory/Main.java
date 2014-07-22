package designpattern.factory.simplefactory;

import designpattern.factory.utils.*;

public class Main {
  public static void main(String[] args) {
    try {
      IFruit apple = FruitGarden.createFruit("Apple");
      apple.plant();
      apple.grow();
      IFruit banana = FruitGarden.createFruit("Banana");
      banana.plant();
      banana.grow();
      IFruit grape = FruitGarden.createFruit("Grape");
      grape.plant();
      grape.grow();
    } catch(Exception e) {}
  }
}
