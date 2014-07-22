package designpattern.factory.factorymethod;

import designpattern.factory.utils.*;

public class AppleGarden implements IFruitGarden {
  @Override
  public IFruit createFruit() {
    return new Apple();
  }
}
