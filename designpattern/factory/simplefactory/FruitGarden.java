package designpattern.factory.simplefactory;

import designpattern.factory.utils.Apple;
import designpattern.factory.utils.Banana;
import designpattern.factory.utils.IFruit;

public class FruitGarden {
  public static IFruit createFruit(String fruitName) throws Exception {
    if(fruitName.equals("Apple")) {
      return new Apple();
    } else if(fruitName.equals("Banana")) {
      return new Banana();
    } else {
      System.out.printf("Sorry! %s not supported.\n", fruitName);
      throw new Exception();
    }
  }
  /* another way to create instance
  public static IFruit createApple() {
    return new Apple();
  }
  public static IFruit createBanana() {
    return new Banana();
  }
  */
}
