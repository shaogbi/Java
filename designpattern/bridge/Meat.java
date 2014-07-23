package designpattern.bridge;

public class Meat implements IFood {
  private final static String MEAT = "meat";
  @Override
  public String getFoodName() {
    return MEAT;
  }
}
