package designpattern.factory.factorymethod;

import designpattern.factory.utils.*;

public class Main {
  public static void main(String[] args) {
    IFruitGarden fruitGarden;
    fruitGarden = new AppleGarden();
    IFruit apple = fruitGarden.createFruit();
    apple.plant();
    apple.grow();
    fruitGarden = new BananaGarden();
    IFruit banana = fruitGarden.createFruit();
    banana.plant();
    banana.grow();
  }
}
