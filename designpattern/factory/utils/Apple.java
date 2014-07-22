package designpattern.factory.utils;

public class Apple implements IFruit {
  @Override
  public void plant() {
    System.out.println("Apple is planted.");
  }
  @Override
  public void grow() {
    System.out.println("Apple is growing."); 
  }
}
