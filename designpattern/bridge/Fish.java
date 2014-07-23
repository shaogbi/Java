package designpattern.bridge;

public class Fish implements IFood {
  private final static String FISH = "fish";
  @Override
  public String getFoodName() {
    return FISH;
  }
}
