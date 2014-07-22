package designpattern.factory.factorymethod;

import designpattern.factory.utils.*;

public class BananaGarden implements IFruitGarden {
  @Override
  public IFruit createFruit() {
    return new Banana();
  }
}
